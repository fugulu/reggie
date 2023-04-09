package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("employee")
@Slf4j
public class EmployeeController extends BaseController {

    @Autowired
    private EmployeeService employeeService;
    /**
     * 登录方法
     */
    @PostMapping("login")
    public R<Employee> login(@RequestBody Employee employee) {
        //1.调用业务层实现登录
        R<Employee> login = employeeService.login(employee);
        //2.如果登录成功
        if (login.getCode() == 1) {
            //获取员工
            Employee dbEmployee = login.getData();
            //将用户ID的信息保存到会话域中，注：键必须叫LOGIN_ID
            session.setAttribute("LOGIN_ID", dbEmployee.getId());
        }
        //3.返回登录的结果
        return login;
    }

    /**
     * 退出
     */
    @PostMapping("logout")
    public R<String> logout() {
        //会话域失效
        session.invalidate();
        //返回操作成功
        return R.success("退出成功");
    }

    /**
     * 添加员工
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        //调用业务层保存
        employeeService.save(employee);
        return R.success("添加员工成功");
    }

    /**
     * 员工分页查询
     * 参数传入的名字叫: page，而接收的形参名字叫pageNum
     */
    @GetMapping("page")
    public R<Page<Employee>> findByPage(@RequestParam("page") Integer pageNum, Integer pageSize, String name) {
        //调用业务层查询得到Page对象
        Page<Employee> page = employeeService.findByPage(pageNum, pageSize, name);
        //封装成结果对象返回
        return R.success(page);
    }

    /**
     * 修改员工
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        //调用业务层更新
        employeeService.update(employee);
        //返回操作结果
        return R.success("修改成功");
    }

    /**
     * 通过id查询员工
     */
    @GetMapping("{id}")
    public R<Employee> findById(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        return R.success(employee);
    }
}
