package com.itheima.reggie.service;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.Page;

/**
 * 员工的业务层
 */
public interface EmployeeService {

    /**
     * 登录方法
     */
    R<Employee> login(Employee employee);

    /**
     * 保存员工
     */
    void save(Employee employee);

    /**
     * 分页查询
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @param name 员工姓名
     */
    Page<Employee> findByPage(Integer pageNum, Integer pageSize, String name);

    /**
     * 修改员工
     */
    void update(Employee employee);

    /**
     * 通过id查询员工
     */
    Employee findById(Long id);
}
