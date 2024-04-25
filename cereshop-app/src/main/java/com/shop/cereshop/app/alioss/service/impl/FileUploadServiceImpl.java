/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.app.alioss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.shop.cereshop.app.alioss.listener.PutObjectProgressListener;
import com.shop.cereshop.app.alioss.service.FileUploadService;
import com.shop.cereshop.app.config.ConstantProperties;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.ImageUtil;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String uploadFile(String filename, InputStream in,long size) throws Exception {
        String fileName= ConstantProperties.ALIOSS_FILE_HOST;
        //设置文件上传路径
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ConstantProperties.ALIOSS_END_POINT,
                ConstantProperties.ALIOSS_ACCESS_KEY_ID, ConstantProperties.ALIOSS_ACCESS_KEY_SECRET);
        String dateStr = format.format(new Date());
        //创建文件路径
        String fileUrl = fileName+"/"+(dateStr + "/" + UUID.randomUUID().toString().replace("-","")+filename);
        InputStream inputStream=in;
        if(size>5*1024*1024){
            //大于5M就压缩
            if(filename.contains("jpg")||filename.contains("png")){
                //如果是图片,压缩一下
                inputStream = ImageUtil.compressImg(in, 1.1f);
            }
        }
        // 上传网络流。
        ossClient.putObject(new PutObjectRequest(ConstantProperties.ALIOSS_BUCKET_NAME, fileUrl, inputStream).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null,0)));
        //设置权限 这里是公开读
        ossClient.setBucketAcl(ConstantProperties.ALIOSS_BUCKET_NAME, CannedAccessControlList.PublicRead);
        //上传成功后，获取文件访问路径
        String prefix=fileUrl.substring(0,fileUrl.indexOf("."));
        List<OSSObjectSummary> list = getFileByPrefix(prefix);
        ossClient.shutdown();
        inputStream.close();
        in.close();
        if(!EmptyUtils.isEmpty(list)){
            URL url = getUrl(list.get(0).getKey());
            // 关闭OSSClient。
            if(url.getProtocol().equals("http")){
                return url.getProtocol()+"s://"+url.getHost()+url.getPath();
            }else {
                return url.getProtocol()+"://"+url.getHost()+url.getPath();
            }
        }
        return null;
    }

    @Override
    public String uploadFile(String filename,byte[] bytes) throws Exception {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ConstantProperties.ALIOSS_END_POINT,
                ConstantProperties.ALIOSS_ACCESS_KEY_ID, ConstantProperties.ALIOSS_ACCESS_KEY_SECRET);
        String dateStr = format.format(new Date());
        //创建文件路径
        String fileUrl = ConstantProperties.ALIOSS_FILE_HOST+"/"+(dateStr + "/" + UUID.randomUUID().toString().replace("-","")+"-"+filename);
        long l = System.currentTimeMillis();
        ossClient.putObject(new PutObjectRequest(ConstantProperties.ALIOSS_BUCKET_NAME, fileUrl,new ByteArrayInputStream(bytes)).
                <PutObjectRequest>withProgressListener(new PutObjectProgressListener(null,0)));
        //设置权限 这里是公开读
        ossClient.setBucketAcl(ConstantProperties.ALIOSS_BUCKET_NAME, CannedAccessControlList.PublicRead);
        //上传成功后，获取文件访问路径
        String prefix=fileUrl.substring(0,fileUrl.indexOf("."));
        List<OSSObjectSummary> list = getFileByPrefix(prefix);
        if(!EmptyUtils.isEmpty(list)){
            URL url = getUrl(list.get(0).getKey());
            // 关闭OSSClient。
            ossClient.shutdown();
            if(url.getProtocol().equals("http")){
                return url.getProtocol()+"s://"+url.getHost()+url.getPath();
            }else {
                return url.getProtocol()+"://"+url.getHost()+url.getPath();
            }
        }
        return null;
    }

    public List<OSSObjectSummary> getFileByPrefix(String prefix) {
        OSSClient ossClient = new OSSClient(ConstantProperties.ALIOSS_END_POINT,
                ConstantProperties.ALIOSS_ACCESS_KEY_ID, ConstantProperties.ALIOSS_ACCESS_KEY_SECRET);
        // 列举包含指定前缀的文件。默认列举100个文件。
        ObjectListing objectListing = ossClient.listObjects(new ListObjectsRequest(ConstantProperties.ALIOSS_BUCKET_NAME).withPrefix(prefix));
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        // 关闭OSSClient。
        ossClient.shutdown();
        return sums;
    }

    public URL getUrl(String key) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(ConstantProperties.ALIOSS_END_POINT,
                ConstantProperties.ALIOSS_ACCESS_KEY_ID, ConstantProperties.ALIOSS_ACCESS_KEY_SECRET);
        //设置过期时间为1小时
        Date expiration=new Date(new Date().getTime()+3600*10000);
        //生成URL
        URL url=ossClient.generatePresignedUrl(ConstantProperties.ALIOSS_BUCKET_NAME,key,expiration);
        return url;
    }
}
