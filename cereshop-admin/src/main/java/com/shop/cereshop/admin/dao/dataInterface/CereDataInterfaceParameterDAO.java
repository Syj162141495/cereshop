package com.shop.cereshop.admin.dao.dataInterface;

import com.shop.cereshop.commons.domain.dataInterface.CereDataInterfaceParameter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Mapper
public interface CereDataInterfaceParameterDAO {

    List<CereDataInterfaceParameter> getDataInterfaceParameterListByDataInterfaceId(Long dataInterfaceId);

    int insertDataInterfaceParameter(CereDataInterfaceParameter cereDataInterfaceParameter);

    int deleteByDataInterfaceId(Long dataInterfaceId);
}
