/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.commission.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
/**
 * StoreDepartmentConfig
 * @version 1.0
 * @author
 */
@Data
public class StoreDepartmentConfigUpdateDTO implements Serializable {
   @ApiModelProperty("id")
    private Long id;

   @ApiModelProperty("taxRate")
    private Long taxRate;

   @ApiModelProperty("orgid")
    private Long orgid;

   @ApiModelProperty("orgType")
    private Integer orgType;


   @ApiModelProperty("createUser")
    private Long createUser;


   @ApiModelProperty("updateUser")
    private Long updateUser;

   @ApiModelProperty("remark")
    private String remark;

}
