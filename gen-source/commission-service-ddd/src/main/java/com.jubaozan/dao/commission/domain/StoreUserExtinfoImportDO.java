/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package com.jubaozan.commission.dao.mapper;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * StoreUserExtinfoImport
 *
 * @author jjw
 * @version 1.0
 *

 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_store_user_extinfo_import")
@ApiModel(value = "StoreUserExtinfoImport", description = "")
public class StoreUserExtinfoImportDO implements Serializable{

    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "importId")
    private Long importId;

    @ApiModelProperty(value = "storeId")
    private Long storeId;

    @ApiModelProperty(value = "workNo")
    private String workNo;

    @ApiModelProperty(value = "mobile")
    private String mobile;

    @ApiModelProperty(value = "departName")
    private String departName;

    @ApiModelProperty(value = "synced")
    private Boolean synced;

    @ApiModelProperty(value = "createAt")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "syncedAt")
    private LocalDateTime syncedAt;

}
