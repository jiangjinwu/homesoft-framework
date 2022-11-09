/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.commission.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Max;
/**
 * StoreDepartmentConfig
 * @version 1.0
 * @author jjw
 */
@Data
public class StoreDepartmentConfigCreationDTO implements Serializable {
        @NotNull
    @ApiModelProperty("id")
    private Long id;


    @ApiModelProperty("taxRate")
    private Long taxRate;


    @ApiModelProperty("orgid")
    private Long orgid;


    @ApiModelProperty("orgType")
    private Integer orgType;

        @NotNull


    @ApiModelProperty("createUser")
    private Long createUser;




    @ApiModelProperty("updateUser")
    private Long updateUser;


    @ApiModelProperty("remark")
    private String remark;


}
