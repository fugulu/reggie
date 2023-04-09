package com.itheima.reggie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    /**
     * 登录方法
     * @param employee
     */
    @Override
    public R<Employee> login(Employee employee) {
        //1.根据用户名查询用户
        Employee dbEmployee = employeeMapper.findByUsername(employee);
        //2.如果找到用户，则判断密码是否正确
        if (dbEmployee == null) {
            return R.error("用户不存在");
        }
        //不为空则表示用户存在
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //参数1：用户输入密码，参数2：加密后密码
        boolean matches = encoder.matches(employee.getPassword(), dbEmployee.getPassword());
        //如果不正确，则返回错误信息
        if (!matches) {
            return R.error("密码错误");
        }
        //3.判断用户是否禁用，1表示可用，0表示禁用
        if (dbEmployee.getStatus() == 0) {
            return R.error("用户被禁用");
        }
        //4.如果上面都通过，则登录成功 (传从数据库中查询出来的用户数据)
        return R.success(dbEmployee);
    }

    /**
     * 保存员工
     * @param employee
     */
    @Override
    public void save(Employee employee) {
        //1.添加用户状态为1
        employee.setStatus(1);

        //2.默认密码设置为123456，密码要加密加盐
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        employee.setPassword(encode);

        //4.调用持久层添加数据
        employeeMapper.save(employee);
    }

    /**
     * 分页查询
     * @param pageNum 当前页
     * @param pageSize 页面大小
     * @param name 员工姓名
     */
    @Override
    public Page<Employee> findByPage(Integer pageNum, Integer pageSize, String name) {
        //1.查询所有记录
        List<Employee> employees = employeeMapper.findByName(name);
        //2.再封装成Page对象返回 (只封装一个属性)
        return new Page<>(employees);
    }

    /**
     * 修改员工
     * @param employee
     */
    @Override
    public void update(Employee employee) {
        //不能禁用admin
        if (employee.getStatus()==0 && employee.getId() == 1) {
            throw new CustomException("不能禁用管理员账户");
        }
        //调用持久层更新
        employeeMapper.update(employee);
    }

    /**
     * 通过id查询员工
     * @param id
     */
    @Override
    public Employee findById(Long id) {
        return employeeMapper.findById(id);
    }
}
