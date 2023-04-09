package com.itheima.reggie.mapper;

import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 员工数据层
 */
public interface EmployeeMapper {

    /**
     * 通过用户名查询一个用户对象
     */
    @Select("SELECT * FROM employee WHERE username=#{username}")
    Employee findByUsername(Employee employee);

    /**
     * 添加员工
     */
    @Insert("INSERT INTO employee VALUES (null,#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void save(Employee employee);

    /**
     * 通过名字模糊查询所有的员工记录
     */
    List<Employee> findByName(@Param("name") String name);

    /**
     * 修改员工
     */
    void update(Employee employee);

    /**
     * 通过id查询员工
     */
    @Select("select * from employee where id=#{id}")
    Employee findById(Long id);
}
