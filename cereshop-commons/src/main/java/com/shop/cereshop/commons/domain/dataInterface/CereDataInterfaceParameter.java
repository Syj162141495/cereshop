package com.shop.cereshop.commons.domain.dataInterface;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Cocoicobird
 * @version 1.0
 */
@Data
@ApiModel(value = "CereDataInterfaceParameter", description = "数据接口参数")
public class CereDataInterfaceParameter implements Serializable {

    @ApiModelProperty(value = "数据接口参数 id")
    private Long dataInterfaceParameterId;

    @ApiModelProperty(value = "数据接口 id")
    private Long dataInterfaceId;

    @ApiModelProperty(value = "数据接口参数类型")
    private String dataInterfaceParameterType;

    @ApiModelProperty(value = "数据接口参数示例")
    private String dataInterfaceParameterExample;
}
