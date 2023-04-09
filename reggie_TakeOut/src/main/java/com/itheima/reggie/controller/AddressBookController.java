package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("addressBook")
public class AddressBookController extends BaseController{

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list() {
        //从会话域中获取当前登录的用户id
        Long userId = getLoginIdFromSession();
        //调用业务层查询当前用户的地址
        List<AddressBook> addressList = addressBookService.queryAddressList(userId);
        return R.success(addressList);
    }

    /**
     * 新增，返回地址对象
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook) {
        //从会话域中获取当前登录的用户id
        Long userId = getLoginIdFromSession();
        //给用户id赋值
        addressBook.setUserId(userId);
        //调用业务层添加
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R<AddressBook> get(@PathVariable Long id) {
        //调用业务层，通过id查询一个地址
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该地址");
        }
    }

    /**
     * 更新地址，返回地址对象
     */
    @PutMapping
    public R<AddressBook> update(@RequestBody AddressBook addressBook) {
        //从会话中获取用户的id
        Long userId = getLoginIdFromSession();
        //设置用户id
        addressBook.setUserId(userId);
        addressBookService.updateAddress(addressBook);
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        //从会话中获取用户的id
        Long userId = getLoginIdFromSession();
        addressBook.setUserId(userId);
        //调用业务层更新默认的收货地址
        addressBookService.updateDefaultAddress(addressBook);
        return R.success(addressBook);
    }

    /**
     * 删除地址，查询字符串的名字是ids
     */
    @DeleteMapping
    public R<String> delete(Long ids) {
        addressBookService.deleteById(ids);
        return R.success("删除成功");
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault() {
        //获取用户id
        Long userId = getLoginIdFromSession();
        //查询用户默认收货地址
        AddressBook addressBook = addressBookService.getDefaultAddress(userId);
        if (addressBook == null) {
            return R.error("当前用户没有默认收货地址");
        } else {
            return R.success(addressBook);
        }
    }
}