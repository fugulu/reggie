package com.itheima.reggie.controller;

import org.apache.commons.io.IOUtils;
import com.itheima.reggie.common.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * 公用的控制器
 */
@Slf4j
@RestController
@RequestMapping("common")
public class CommonController extends BaseController {

    @Value("${reggie.path}")
    private String imgPath;

    /**
     * 文件上传
     * 1. 方法要提供MultipartFile形参类型
     * 2. 方法形参名字与文件域的名字一样，如：file
     */
    @SneakyThrows
    @PostMapping("upload")
    public R<String> upload(MultipartFile file) {
        //获取原始文件名
        String originalFilename = file.getOriginalFilename();  //原始文件：food.jpg

        //避免同名文件覆盖，需要将上传的文件名改名
        String mainFile = UUID.randomUUID().toString();  //主文件名：87fea020-494d-11ed-8004-000ec6c49bf4
        //得到最后一个点号的位置
        int start = originalFilename.lastIndexOf(".");
        //从点号开始取到最后字符串
        String suffix = originalFilename.substring(start);  //.jpg 扩展名
        //得到新的文件名
        String picFileName = mainFile + suffix;  // 87fea020-494d-11ed-8004-000ec6c49bf4.jpg

        //上传到服务器， File(目录名,文件名)
        file.transferTo(new File(imgPath, picFileName));

        log.info("上传图片{} 到目录 {}：", picFileName, imgPath);
        return R.success(picFileName);
    }

    /**
     * 显示图片
     */
    @SneakyThrows
    @GetMapping("/download")
    public void download(String name){
        //1.获取文件输入流
        FileInputStream inputStream = new FileInputStream(imgPath + name);
        //2.得到响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //3.IOUtils.copy(输入流、输出流)
        IOUtils.copy(inputStream, outputStream);
        //4.关闭流
        inputStream.close();
        outputStream.close();
    }
}
