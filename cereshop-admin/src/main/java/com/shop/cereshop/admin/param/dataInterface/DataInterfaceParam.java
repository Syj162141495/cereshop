package com.shop.cereshop.admin.param.dataInterface;

import com.shop.cereshop.commons.domain.dataInterface.CereDataInterfaceParameter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Data
@ApiModel(value = "DataInterfaceParam", description = "数据接口参数")
public class DataInterfaceParam implements Serializable {

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

    @ApiModelProperty(value = "数据接口参数列表")
    private List<CereDataInterfaceParameter> dataInterfaceParameterList;

    @ApiModelProperty(value = "当前页")
    private Integer pageNumber;

    @ApiModelProperty(value = "每页记录数")
    private Integer pageSize;
}
