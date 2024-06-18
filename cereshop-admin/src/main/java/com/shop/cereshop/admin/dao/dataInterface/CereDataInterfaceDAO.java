package com.shop.cereshop.admin.dao.dataInterface;

import com.shop.cereshop.admin.param.dataInterface.DataInterfaceParam;
import com.shop.cereshop.commons.domain.dataInterface.CereDataInterface;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Mapper
public interface CereDataInterfaceDAO {

    int insertDataInterface(CereDataInterface cereDataInterface);

    int updateByDataInterfaceId(CereDataInterface cereDataInterface);

    int deleteByDataInterfaceId(Long dataInterfaceId);

    List<CereDataInterface> getDataInterfaceList(DataInterfaceParam dataInterfaceParam);
}
