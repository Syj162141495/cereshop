/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.label.*;
import com.shop.cereshop.admin.service.label.CerePlatformLabelService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.label.PlatformLabel;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.poi.ExcelUtils;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 标签管理
 */
@RestController
@RequestMapping("label")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "PlatformLabelController")
@Api(value = "标签管理", tags = "标签管理")
public class PlatformLabelController {

    @Autowired
    private CerePlatformLabelService cerePlatformLabelService;

    /**
     * 标签管理查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "标签管理查询")
    public Result<Page<PlatformLabel>> getAll(@RequestBody LabelGetAllParam param) throws CoBusinessException{
        Page page=cerePlatformLabelService.getAll(param);
        return new Result(page);
    }

    /**
     * 标签编辑查询
     * @param param
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "标签编辑查询")
    public Result<PlatformLabel> getById(@RequestBody LabelGetByIdParam param) throws CoBusinessException{
        PlatformLabel label=cerePlatformLabelService.getById(param.getBuyerLabelId());
        return new Result(label);
    }

    /**
     * 添加标签
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加标签")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated LabelSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformLabelService.save(param,user);
        return new Result(user.getUsername(),"添加标签", GsonUtil.objectToGson(param));
    }

    /**
     * 修改标签
     * @param label
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改标签")
    @NoRepeatWebLog
    public Result update(@RequestBody LabelUpdateParam label, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformLabelService.update(label,user);
        return new Result(user.getUsername(),"修改标签", GsonUtil.objectToGson(label));
    }

    /**
     * 删除标签
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除标签")
    @NoRepeatWebLog
    public Result delete(@RequestBody LabelDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformLabelService.delete(param,user);
        return new Result(user.getUsername(),"删除标签", GsonUtil.objectToGson(param));
    }

    /**
     * 标签导出
     * @param param
     * @param response
     */
    @RequestMapping(value = "excel_platform_label",method = RequestMethod.POST)
    @ApiOperation(value = "标签导出")
    public void excelLabel(@RequestBody LabelExcelParam param, HttpServletResponse response) throws CoBusinessException,Exception{
        List<PlatformLabel> list=cerePlatformLabelService.findExcel(param);
        List<String> titles=new ArrayList<>();
        titles.add("标签名");
        titles.add("客户");
        titles.add("标签类型");
        titles.add("达标条件");
        XSSFWorkbook excel = ExcelUtils.createPlatformLabelExcel(titles, list);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = "订单管理表"+sdf.format(date);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + str +".xls");// 默认Excel名称
        response.flushBuffer();
        excel.write(response.getOutputStream());
        excel.close();
    }
}
