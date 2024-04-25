package com.shop.cereshop.commons.domain.tool;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel
@Data
public class CereComposeMergeInfo implements Serializable {

    private static final long serialVersionUID = -2580548659864939995L;

    /**
     * 组合捆绑id
     */
    private Long composeId;

    /**
     * 组合捆绑名称
     */
    private String composeName;

    /**
     * 优惠类型
     */
    private Integer composeType;

    /**
     * 优惠值(元/折)
     */
    private BigDecimal promote;

    /**
     * 商品id列表
     */
    private List<Long> productIdList;

}
