package com.shop.cereshop.admin.page.dataInterface;

import com.shop.cereshop.commons.domain.dataInterface.CereDataInterfaceParameter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Data
@ApiModel(value = "DataInterface", description = "数据接口返回实体类")
public class DataInterface {

    @ApiModelProperty(value = "数据接口 id")
    private Long dataInterfaceId;

    @ApiModelProperty(value = "系统模块名称")
    private String systemModuleName;

    @ApiModelProperty(value = "数据接口名称")
    private String dataInterfaceName;

    @ApiModelProperty(value = "数据接口请求方式")
    private String dataInterfaceHttpMethod;

    @ApiModelProperty(value = "数据接口请求地址")
    private String dataInterfaceUrl;

    @ApiModelProperty(value = "数据接口返回参数类型")
    private String dataInterfaceReturnType;

    @ApiModelProperty(value = "数据接口返回参数示例")
    private String dataInterfaceReturnTypeExample;

    @ApiModelProperty(value = "数据接口参数 id 列表")
    private List<CereDataInterfaceParameter> dataInterfaceParameterList;
}
