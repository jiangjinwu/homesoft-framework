/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package com.jubaozan.commission.service.dao.mapper;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * StoreDepartmentConfig
 *
 * @author jjw
 * @version 1.0
 *

 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_store_department_config")
@ApiModel(value = "StoreDepartmentConfig", description = "")
public class StoreDepartmentConfigDO implements Serializable{

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "taxRate")
    private Long taxRate;

    @ApiModelProperty(value = "orgid")
    private Long orgid;

    @ApiModelProperty(value = "orgType")
    private Integer orgType;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "createUser")
    private Long createUser;

    @ApiModelProperty(value = "updateAt")
    private LocalDateTime updateAt;

    @ApiModelProperty(value = "updateUser")
    private Long updateUser;

    @ApiModelProperty(value = "remark")
    private String remark;

}
