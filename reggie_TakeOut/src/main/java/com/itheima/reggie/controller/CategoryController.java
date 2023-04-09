package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Page;
import com.itheima.reggie.service.CategoryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController extends BaseController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        //1.调用业务层添加
        categoryService.save(category);
        //2.返回添加成功
        return R.success("添加分类成功");
    }

    /**
     * 分页查询
     */
    @GetMapping("page")
    public R<Page<Category>> findByPage(@RequestParam("page")Integer pageNum, Integer pageSize) {
        //调用业务层查询
        Page<Category> page = categoryService.findByPage(pageNum, pageSize);
        return R.success(page);
    }

    /**
     * 通过id删除分类
     */
    @DeleteMapping
    public R<String> delete(Long id) {
        categoryService.deleteById(id);
        return R.success("分类删除成功");
    }

    /**
     * 修改
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.update(category);
        return R.success("修改成功");
    }

    /**
     * 导出成Excel文件
     */
    @SneakyThrows
    @GetMapping("exportExcel")
    public void exportExcel() {
        //指定响应类型为Office Excel格式
        response.setContentType("application/vnd.ms-excel");
        //设置响应头，指定为下载，并且指定下载文件名(汉字会有乱码)
        response.setHeader("content-disposition", "attachment;filename=category.xlsx");

        //1. 读取模板Excel文件 (把category.xlsx文件复制到resources/excel目录下)
        //使用类对象读取类路径下输入流
        InputStream inputStream = getClass().getResourceAsStream("/excel/category_template.xlsx");
        //创建一个工作簿
        Workbook workbook = new XSSFWorkbook(inputStream);
        //获取第0个工作表
        Sheet sheet = workbook.getSheetAt(0);

        //2. 读取其中第2行的样式保存到一个集合中
        Row row = sheet.getRow(1);
        //创建一个集合用于存储上面这一行样式
        List<CellStyle> cellStyleList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //获取每个单元格的样式
            CellStyle cellStyle = row.getCell(i).getCellStyle();
            //添加到集合中
            cellStyleList.add(cellStyle);
        }

        //3. 读取数据库中的数据，填充到Excel中，并且使用样式
        //调用业务层查询出所有分类数据
        List<Category> categoryList = categoryService.findAll();
        for (int i = 0; i < categoryList.size(); i++) {
            //获取每条记录 -> 对应Excel中一行
            Category category = categoryList.get(i);
            //创建一行
            row = sheet.createRow(i + 1);

            //创建单元格 (类型)
            Cell cell = row.createCell(0);
            //设置单元格值
            cell.setCellValue(category.getType());
            //设置样式
            cell.setCellStyle(cellStyleList.get(0));

            //创建单元格 (名字)
            cell = row.createCell(1);
            //设置单元格值
            cell.setCellValue(category.getName());
            //设置样式
            cell.setCellStyle(cellStyleList.get(1));

            //创建单元格 (排序)
            cell = row.createCell(2);
            //设置单元格值
            cell.setCellValue(category.getSort());
            //设置样式
            cell.setCellStyle(cellStyleList.get(2));

            //创建单元格 (创建时间)
            cell = row.createCell(3);
            //设置单元格值
            cell.setCellValue(category.getCreateTime().toLocalDate().toString());
            //设置样式
            cell.setCellStyle(cellStyleList.get(3));

            //创建单元格 (修改时间)
            cell = row.createCell(4);
            //设置单元格值
            cell.setCellValue(category.getUpdateTime().toLocalDate().toString());
            //设置样式
            cell.setCellStyle(cellStyleList.get(4));
        }

        //4. 输出到浏览器上
        workbook.write(response.getOutputStream());
    }

    /**
     * 通过菜品或套餐类型查询分类
     */
    @GetMapping("list")
    public R<List<Category>> list(Integer type) {
        List<Category> categories = categoryService.list(type);
        return R.success(categories);
    }
}
