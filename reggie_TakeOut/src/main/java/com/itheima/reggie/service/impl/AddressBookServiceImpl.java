package com.itheima.reggie.service.impl;

import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.mapper.AddressBookMapper;
import com.itheima.reggie.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Projectname: reggie
 * @Filename: AddressBookServiceImpl
 * @Author: Steven
 * @Data:2022-10-15 10:38
 */

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 查询当前用户所有的地址
     * @param userId
     */
    @Override
    public List<AddressBook> queryAddressList(Long userId) {
        return addressBookMapper.queryAddressList(userId);
    }

    /**
     * 保存地址
     * @param addressBook
     */
    @Override
    public void save(AddressBook addressBook) {
        addressBookMapper.save(addressBook);
    }

    /**
     * 根据id查询
     * @param id
     */
    @Override
    public AddressBook getById(Long id) {
        return addressBookMapper.getById(id);
    }

    /**
     * 更新地址
     * @param addressBook
     */
    @Override
    public void updateAddress(AddressBook addressBook) {
        addressBookMapper.updateAddress(addressBook);
    }

    /**
     * 更新默认地址
     * @param addressBook
     */
    @Override
    @Transactional
    public void updateDefaultAddress(AddressBook addressBook) {
        //1.    把该用户的所有的地址全改成非默认
        addressBookMapper.removeDefaultAddress(addressBook.getUserId());
        //2.    重新设置当前地址为默认地址
        addressBookMapper.updateDefaultAddress(addressBook);
    }

    /**
     * 根据id删除地址
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        //先查询这条地址是否为默认地址，如果是就不能删除
        AddressBook addressBook = getById(id);
        if (addressBook.getIsDefault() == 1) {
            throw new CustomException("要删除的是默认地址，不能删除");
        }
        //不是默认地址则删除
        addressBookMapper.deleteById(id);
    }

    /**
     * 得到某个用户的默认地址
     * @param userId
     */
    @Override
    public AddressBook getDefaultAddress(Long userId) {
        return addressBookMapper.getDefaultAddress(userId);
    }
}
