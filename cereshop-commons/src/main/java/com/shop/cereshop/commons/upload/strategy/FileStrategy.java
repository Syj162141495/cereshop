/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.upload.strategy;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * 文件策略接口
 *
 * @author ceres
 * @date 2019/06/17
 */
public interface FileStrategy {
    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件对象
     * @author ceres
     * @date 2019-05-06 16:38
     */
    String upload(MultipartFile file) throws Exception;

    /**
     * 文件上传
     *
     * @param fileName 文件名称
     * @param bytes 字节流
     * @param inputStream 输入流
     * @parm size 文件大小
     * @return 文件对象
     * @author ceres
     * @date 2019-05-06 16:38
     */
    String uploadStream(String fileName, byte[] bytes, InputStream inputStream, long size) throws Exception;

}
