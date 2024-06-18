package com.shop.cereshop.admin.service.dataInterface.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.cereshop.admin.dao.dataInterface.CereDataInterfaceDAO;
import com.shop.cereshop.admin.dao.dataInterface.CereDataInterfaceParameterDAO;
import com.shop.cereshop.admin.page.dataInterface.DataInterface;
import com.shop.cereshop.admin.param.dataInterface.DataInterfaceParam;
import com.shop.cereshop.admin.service.dataInterface.CereDataInterfaceService;
import com.shop.cereshop.commons.domain.common.Page;
import com.shop.cereshop.commons.domain.dataInterface.CereDataInterface;
import com.shop.cereshop.commons.domain.dataInterface.CereDataInterfaceParameter;
import com.shop.cereshop.commons.exception.CoBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Service
public class CereDataInterfaceServiceImpl implements CereDataInterfaceService {

    @Autowired
    private CereDataInterfaceDAO cereDataInterfaceDAO;

    @Autowired
    private CereDataInterfaceParameterDAO cereDataInterfaceParameterDAO;

    /**
     * 获取数据接口列表
     */
    @Override
    public Page<DataInterface> getDataInterfaceList(DataInterfaceParam dataInterfaceParam) {
        PageHelper.startPage(dataInterfaceParam.getPageNumber(), dataInterfaceParam.getPageSize());
        List<CereDataInterface> dataInterfaceList = cereDataInterfaceDAO.getDataInterfaceList(dataInterfaceParam);
        PageInfo<CereDataInterface> pageInfo = new PageInfo<>(dataInterfaceList);
        List<DataInterface> result = new ArrayList<>();
        if (pageInfo.getList() != null) {
            for (CereDataInterface cereDataInterface : pageInfo.getList()) {
                DataInterface dataInterface = new DataInterface();
                dataInterface.setDataInterfaceId(cereDataInterface.getDataInterfaceId());
                dataInterface.setSystemModuleName(cereDataInterface.getSystemModuleName());
                dataInterface.setDataInterfaceName(cereDataInterface.getDataInterfaceName());
                dataInterface.setDataInterfaceHttpMethod(cereDataInterface.getDataInterfaceHttpMethod());
                dataInterface.setDataInterfaceUrl(cereDataInterface.getDataInterfaceUrl());
                dataInterface.setDataInterfaceReturnType(cereDataInterface.getDataInterfaceReturnType());
                dataInterface.setDataInterfaceReturnTypeExample(cereDataInterface.getDataInterfaceReturnTypeExample());
                dataInterface.setDataInterfaceParameterList(cereDataInterfaceParameterDAO.getDataInterfaceParameterListByDataInterfaceId(cereDataInterface.getDataInterfaceId()));
                result.add(dataInterface);
            }
        }
        return new Page<>(result, pageInfo.getTotal());
    }

    /**
     * 新增数据接口
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public int insertDataInterface(DataInterfaceParam dataInterfaceParam) {
        CereDataInterface cereDataInterface = transfer(dataInterfaceParam);
        int count = cereDataInterfaceDAO.insertDataInterface(cereDataInterface);
        for (CereDataInterfaceParameter cereDataInterfaceParameter : dataInterfaceParam.getDataInterfaceParameterList()) {
            cereDataInterfaceParameter.setDataInterfaceId(cereDataInterface.getDataInterfaceId());
            count += cereDataInterfaceParameterDAO.insertDataInterfaceParameter(cereDataInterfaceParameter);
        }
        return count;
    }

    /**
     * 更新数据接口
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public int updateDataInterface(DataInterfaceParam dataInterfaceParam) {
        int count = cereDataInterfaceDAO.updateByDataInterfaceId(transfer(dataInterfaceParam));
        count += cereDataInterfaceParameterDAO.deleteByDataInterfaceId(dataInterfaceParam.getDataInterfaceId());
        for (CereDataInterfaceParameter cereDataInterfaceParameter : dataInterfaceParam.getDataInterfaceParameterList()) {
            count += cereDataInterfaceParameterDAO.insertDataInterfaceParameter(cereDataInterfaceParameter);
        }
        return count;
    }

    /**
     * 删除数据接口
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {CoBusinessException.class, Exception.class})
    public int deleteDataInterface(DataInterfaceParam dataInterfaceParam) {
        int count = cereDataInterfaceParameterDAO.deleteByDataInterfaceId(dataInterfaceParam.getDataInterfaceId());
        count += cereDataInterfaceDAO.deleteByDataInterfaceId(dataInterfaceParam.getDataInterfaceId());
        return count;
    }

    private CereDataInterface transfer(DataInterfaceParam dataInterfaceParam) {
        CereDataInterface cereDataInterface = new CereDataInterface();
        cereDataInterface.setDataInterfaceId(dataInterfaceParam.getDataInterfaceId());
        cereDataInterface.setSystemModuleName(dataInterfaceParam.getSystemModuleName());
        cereDataInterface.setDataInterfaceName(dataInterfaceParam.getDataInterfaceName());
        cereDataInterface.setDataInterfaceHttpMethod(dataInterfaceParam.getDataInterfaceHttpMethod());
        cereDataInterface.setDataInterfaceUrl(dataInterfaceParam.getDataInterfaceUrl());
        cereDataInterface.setDataInterfaceReturnType(dataInterfaceParam.getDataInterfaceReturnType());
        cereDataInterface.setDataInterfaceReturnTypeExample(dataInterfaceParam.getDataInterfaceReturnTypeExample());
        return cereDataInterface;
    }

}
