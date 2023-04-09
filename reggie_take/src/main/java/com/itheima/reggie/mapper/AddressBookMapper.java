package com.itheima.reggie.mapper;

/**
 * @Projectname: reggie
 * @Filename: AddressBookMapper
 * @Author: Steven
 * @Data:2022-10-15 10:24
 */

import com.itheima.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 收货地址数据层
 */
public interface AddressBookMapper {
    /**
     * 查询某个用户所有的地址列表，按更新时间的降序排序
     */
    @Select("select * from address_book where user_id=#{userId} order by update_time desc")
    List<AddressBook> queryAddressList(Long userId);

    /**
     * 添加地址
     */
    @Insert("insert into address_book(user_id,consignee,phone,sex,detail,label,create_time,update_time,create_user,update_user) values(#{userId},#{consignee},#{phone},#{sex},#{detail},#{label},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void save(AddressBook addressBook);

    /**
     * 通过id查询1个地址
     */
    @Select("select * from address_book where id=#{id}")
    AddressBook getById(Long id);

    /**
     * 通过id更新所有地址信息
     * 没有修改：默认收货地址，创建时间，创建用户的字段
     */
    @Update("UPDATE address_book SET user_id = #{userId},consignee = #{consignee},sex = #{sex},phone = #{phone},province_code = #{provinceCode},province_name = #{provinceName},city_code = #{cityCode},city_name = #{cityName},district_code = #{districtCode},district_name = #{districtName},detail = #{detail},label = #{label},update_time = #{updateTime},update_user = #{updateUser} WHERE id = #{id}")
    void updateAddress(AddressBook addressBook);

    /**
     * 去除某个用户的默认地址
     */
    @Update("update address_book set is_default=0 where user_id=#{userId}")
    void removeDefaultAddress(Long userId);

    /**
     * 设置某个地址为默认地址
     */
    @Update("update address_book set is_default=1 where id=#{id}")
    void updateDefaultAddress(AddressBook addressBook);

    /**
     * 通过id删除一条地址
     */
    @Delete("delete from address_book where id=#{id}")
    void deleteById(Long id);

    /**
     * 查询某个用户的默认地址
     */
    @Select("select * from address_book where user_id = #{userId} and is_default = 1")
    AddressBook getDefaultAddress(Long userId);
}
