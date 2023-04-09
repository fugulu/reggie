package com.itheima.reggie.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 分页插件测试
 */
@SpringBootTest
public class PageHelperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void testFindByName() {
        int pageNum = 2;
        int pageSize = 5;
        //1.设置当前页码和大小
        PageHelper.startPage(pageNum, pageSize);

        //2.查询所有，分页组件会自动生成2条SQL语句
        List<Employee> employees = employeeMapper.findByName(null);

        //3.封装成PageInfo对象
        PageInfo<Employee> pageInfo = new PageInfo<>(employees);
        System.out.println("总页数：" + pageInfo.getPages());
        System.out.println("总记录数：" + pageInfo.getTotal());
        List<Employee> list = pageInfo.getList();
        System.out.println("当前页数据：");
        list.forEach(System.out::println);
        System.out.println("页码：" + pageInfo.getPageNum());
        System.out.println("页大小：" + pageInfo.getPageSize());
    }
}
