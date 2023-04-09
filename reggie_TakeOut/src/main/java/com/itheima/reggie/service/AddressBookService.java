package com.itheima.reggie.service;

import com.itheima.reggie.entity.AddressBook;

import java.util.List;

/**
 * 地址业务层
 */
public interface AddressBookService {

    /**
     * 查询当前用户所有的地址
     */
    List<AddressBook> queryAddressList(Long userId);

    /**
     * 保存地址
     * @param addressBook
     */
    void save(AddressBook addressBook);

    /**
     * 根据id查询
     */
    AddressBook getById(Long id);

    /**
     * 更新地址
     * @param addressBook
     */
    void updateAddress(AddressBook addressBook);

    /**
     * 更新默认地址
     * @param addressBook
     */
    void updateDefaultAddress(AddressBook addressBook);

    /**
     * 通过id删除地址
     */
    void deleteById(Long id);

    /**
     * 得到某个用户默认地址
     * @param userId
     * @return
     */
    AddressBook getDefaultAddress(Long userId);
}