/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.annotation.NoRepeatSubmit;
import com.shop.cereshop.admin.annotation.NoRepeatWebLog;
import com.shop.cereshop.admin.param.dict.*;
import com.shop.cereshop.admin.service.dict.CerePlatformDictService;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.constant.LongEnum;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.dict.CerePlatformDict;
import com.shop.cereshop.commons.domain.user.CerePlatformUser;
import com.shop.cereshop.commons.exception.CoBusinessException;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.EmptyUtils;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 数据字典
 */
@RestController
@RequestMapping("dict")
/**
 * 注解方式生成日志对象，指定topic生成对象类名
 */
@Slf4j(topic = "DictController")
@Api(value = "数据字典模块", tags = "数据字典模块")
public class DictController {

    @Autowired
    private CerePlatformDictService cerePlatformDictService;

    /**
     * 添加数据字典
     * @param param
     * @return
     */
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "添加数据字典")
    @NoRepeatWebLog
    public Result save(@RequestBody @Validated DictSaveParam param, HttpServletRequest request) throws CoBusinessException{
        //校验字典名称
        CerePlatformDict cerePlatformDict=cerePlatformDictService.checkName(param.getDictName());
        if(cerePlatformDict!=null){
            return new Result(CoReturnFormat.DICT_NAME_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDictService.save(param,user);
        return new Result(user.getUsername(),"添加数据字典", GsonUtil.objectToGson(param));
    }

    /**
     * 修改数据字典
     * @param param
     * @return
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "修改数据字典")
    @NoRepeatWebLog
    public Result update(@RequestBody DictUpdateParam param, HttpServletRequest request) throws CoBusinessException{
        if(param.getDictId()<=1884l&& LongEnum.ROOT.getCode().equals(param.getDictPid())){
            //如果是初始化数据字典且是根节点,不允许编辑
            return new Result(CoReturnFormat.DICT_NOT_OPERATION);
        }
        //校验字典名称
        CerePlatformDict cerePlatformDict=cerePlatformDictService.checkNameAndId(param);
        if(cerePlatformDict!=null){
            return new Result(CoReturnFormat.DICT_NAME_ALREADY);
        }
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        cerePlatformDictService.update(param,user);
        return new Result(user.getUsername(),"修改数据字典", GsonUtil.objectToGson(param));
    }

    /**
     * 删除数据字典
     * @param param
     * @return
     */
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "删除数据字典")
    @NoRepeatWebLog
    public Result delete(@RequestBody DictDeleteParam param, HttpServletRequest request) throws CoBusinessException{
        //获取当前登录账户
        CerePlatformUser user = (CerePlatformUser) request.getAttribute("user");
        //查询字典数据
        CerePlatformDict dict=cerePlatformDictService.getById(param.getDictId());
        if(dict!=null){
            if(dict.getDictId()<=1884l&& LongEnum.ROOT.getCode().equals(dict.getDictPid())){
                //如果是初始化数据字典且是根节点,不允许该操作
                return new Result(CoReturnFormat.DICT_NOT_OPERATION);
            }
            cerePlatformDictService.delete(param,user);
        }
        return new Result(user.getUsername(),"删除数据字典", GsonUtil.objectToGson(param));
    }

    /**
     * 数据字典编辑查询
     * @return
     */
    @RequestMapping(value = "getById",method = RequestMethod.POST)
    @ApiOperation(value = "数据字典编辑查询")
    public Result<CerePlatformDict> getById(@RequestBody DictGetByIdParam param) throws CoBusinessException{
        CerePlatformDict cerePlatformDict=cerePlatformDictService.getById(param.getDictId());
        return new Result(cerePlatformDict);
    }

    /**
     * 数据字典管理查询
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.POST)
    @ApiOperation(value = "数据字典管理查询")
    public Result<Page<CerePlatformDict>> getAll(@RequestBody DictGetAllParam param) throws CoBusinessException{
        Page page =cerePlatformDictService.getAll(param);
        return new Result(page);
    }

    /**
     * 根据父节点id查询子级字典数据
     * @return
     */
    @RequestMapping(value = "getChilds",method = RequestMethod.POST)
    @ApiOperation(value = "根据父节点id查询子级字典数据")
    public Result<Page<CerePlatformDict>> getChilds(@RequestBody DictGetChildsParam param) throws CoBusinessException{
        Page page =cerePlatformDictService.getChilds(param);
        return new Result(page);
    }

    /**
     * 字典下拉数据查询
     * @return
     */
    @RequestMapping(value = "getSelect",method = RequestMethod.POST)
    @ApiOperation(value = "字典下拉数据查询")
    public Result<List<CerePlatformDict>> getSelect(@RequestBody DictGetSelectParam param) throws CoBusinessException{
        List<CerePlatformDict> list =cerePlatformDictService.getSelect(param.getDictName());
        return new Result(list);
    }

    /**
     * 导入快递公司编码数据
     * @param file
     * @return
     */
    @RequestMapping(value = "importExpressCodes",method = RequestMethod.POST)
    @NoRepeatSubmit
    @ApiOperation(value = "导入快递公司编码数据",notes = "file:文件")
    public Result importExpressCodes(MultipartFile file) throws CoBusinessException,Exception{
        if(EmptyUtils.isEmpty(file)){
            return new Result(CoReturnFormat.NOT_FILE);
        }
        Workbook wb= WorkbookFactory.create(file.getInputStream());
        cerePlatformDictService.importExpressCodes(wb);
        return new Result();
    }
}
