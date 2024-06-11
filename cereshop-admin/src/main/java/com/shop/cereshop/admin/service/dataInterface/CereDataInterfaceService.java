package com.shop.cereshop.admin.service.dataInterface;

import com.shop.cereshop.admin.page.dataInterface.DataInterface;
import com.shop.cereshop.admin.param.dataInterface.DataInterfaceParam;
import com.shop.cereshop.commons.domain.common.Page;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
public interface CereDataInterfaceService {

    Page<DataInterface> getDataInterfaceList(DataInterfaceParam dataInterfaceParam);

    int insertDataInterface(DataInterfaceParam dataInterfaceParam);

    int updateDataInterface(DataInterfaceParam dataInterfaceParam);

    int deleteDataInterface(DataInterfaceParam dataInterfaceParam);
}
