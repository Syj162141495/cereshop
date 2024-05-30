/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.alioss.page.Url;
import com.shop.cereshop.admin.alioss.service.FileUploadService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.upload.strategy.FileStrategy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

/**
 * 文件上传
 */
@RestController
@RequestMapping("file")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "UploadController")
@Api(value = "文件上传", tags = "文件上传")
public class UploadController {

    @Autowired
    private FileStrategy fileStrategy;

    @Value("${upload.storage-path}")
    private String storagePath;

    /**
     * 文件上传
     * @param file
     */
    @PostMapping("upload")
    @ApiOperation(value = "文件上传")
    public Result<Url> upload(MultipartFile file) throws Exception{
        Result result=new Result();
        if(null != file){
            if(file.getOriginalFilename().contains("mp4")){
                //如果上传的视频,校验大小不能超过2M
                if(file.getSize()>2048*1024){
                    return new Result(CoReturnFormat.MP4_FILE_NOT_2M);
                }
            }
            String url=fileStrategy.upload(file);
            result=new Result(new Url(url));
        }
        return result;
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "file/{year}/{month}/{fileName}",method = RequestMethod.GET)
    @ApiOperation(value = "文件上传")
    public void file(@PathVariable(name = "year") String year,
                     @PathVariable(name = "month") String month,
                     @PathVariable(name = "fileName") String fileName,
                     HttpServletResponse response) throws Exception{
        // web服务器存放的绝对路径
        String absolutePath = Paths.get(storagePath, "file", year, month, fileName).toString();
        try {
            FileInputStream inputStream = new FileInputStream(absolutePath);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            String diskfilename = "final.avi";
            response.setContentType("video/avi");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"");
            System.out.println("data.length " + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            OutputStream os = response.getOutputStream();

            os.write(data);
            //先声明的流后关掉！
            os.flush();
            os.close();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
