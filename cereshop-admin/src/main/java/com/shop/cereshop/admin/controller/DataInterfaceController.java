package com.shop.cereshop.admin.controller;

import com.shop.cereshop.admin.page.dataInterface.DataInterface;
import com.shop.cereshop.admin.param.dataInterface.DataInterfaceParam;
import com.shop.cereshop.admin.service.dataInterface.CereDataInterfaceService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.result.Result;
import com.shop.cereshop.commons.utils.GsonUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@RestController
@RequestMapping("dataInterface")
@Slf4j(topic = "DataInterfaceController")
@Api(value = "数据接口模块", tags = "数据接口模块")
public class DataInterfaceController {

    @Autowired
    private CereDataInterfaceService cereDataInterfaceService;

    @RequestMapping(value = "getDataInterfaceList", method = RequestMethod.POST)
    public Result<Page<DataInterface>> getDataInterfaceList(@RequestBody DataInterfaceParam dataInterfaceParam) {
        cereDataInterfaceService.getDataInterfaceList(dataInterfaceParam);
        return new Result<>(cereDataInterfaceService.getDataInterfaceList(dataInterfaceParam));
    }

    @RequestMapping(value = "insertDataInterface", method = RequestMethod.POST)
    public Result<Object> insertDataInterfaceList(@RequestBody DataInterfaceParam dataInterfaceParam,
                                                  HttpServletRequest request) {
        cereDataInterfaceService.insertDataInterface(dataInterfaceParam);
        return new Result<>(request.toString(), "新增数据接口", GsonUtil.objectToGson(dataInterfaceParam));
    }

    @RequestMapping(value = "updateDataInterface", method = RequestMethod.POST)
    public Result<Object> updateDataInterface(@RequestBody DataInterfaceParam dataInterfaceParam,
                                              HttpServletRequest request) {
        cereDataInterfaceService.updateDataInterface(dataInterfaceParam);
        return new Result<>(request.toString(), "修改数据接口", GsonUtil.objectToGson(dataInterfaceParam));
    }

    @RequestMapping(value = "deleteDataInterface", method = RequestMethod.DELETE)
    public Result<Object> deleteDataInterface(@RequestBody DataInterfaceParam dataInterfaceParam, HttpServletRequest request) {
        cereDataInterfaceService.deleteDataInterface(dataInterfaceParam);
        return new Result<>(request.toString(), "删除数据接口", GsonUtil.objectToGson(dataInterfaceParam));
    }

}
