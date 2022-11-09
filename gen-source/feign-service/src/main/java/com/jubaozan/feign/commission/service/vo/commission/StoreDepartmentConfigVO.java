/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.commission.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * StoreDepartmentConfig
 * @version 1.0
 * @author
 */
@Data
public class StoreDepartmentConfigVO implements Serializable {

    // id
     @ApiModelProperty("id")
    private Long id;
    // taxRate
     @ApiModelProperty("taxRate")
    private Long taxRate;
    // orgid
     @ApiModelProperty("orgid")
    private Long orgid;
    // orgType
     @ApiModelProperty("orgType")
    private Integer orgType;
    // 创建时间
     @ApiModelProperty("创建时间")
    private LocalDateTime createAt;
    // createUser
     @ApiModelProperty("createUser")
    private Long createUser;
    // updateAt
     @ApiModelProperty("updateAt")
    private LocalDateTime updateAt;
    // updateUser
     @ApiModelProperty("updateUser")
    private Long updateUser;
    // remark
     @ApiModelProperty("remark")
    private String remark;
}
