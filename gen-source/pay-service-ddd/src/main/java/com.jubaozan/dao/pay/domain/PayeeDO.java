/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package com.jubaozan.pay.dao.mapper;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Payee
 *
 * @author jjw
 * @version 1.0
 *

 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_payee")
@ApiModel(value = "Payee", description = "")
public class PayeeDO implements Serializable{

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "storeId")
    private Long storeId;

    @ApiModelProperty(value = "payeeStoreId")
    private Long payeeStoreId;

    @ApiModelProperty(value = "payeeMerchantId")
    private Long payeeMerchantId;

    @ApiModelProperty(value = "tenantId")
    private String tenantId;

    @ApiModelProperty(value = "createAt")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "createBy")
    private Long createBy;

}
