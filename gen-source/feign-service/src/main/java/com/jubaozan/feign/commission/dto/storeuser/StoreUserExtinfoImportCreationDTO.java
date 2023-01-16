/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.storeuser.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Max;
/**
 * StoreUserExtinfoImport
 * @version 1.0
 * @author jjw
 */
@Data
public class StoreUserExtinfoImportCreationDTO implements Serializable {
        @NotNull
    @ApiModelProperty("id")
    private Long id;


    @ApiModelProperty("userId")
    private Long userId;


    @ApiModelProperty("importId")
    private Long importId;


    @ApiModelProperty("storeId")
    private Long storeId;


    @ApiModelProperty("workNo")
    private String workNo;


    @ApiModelProperty("mobile")
    private String mobile;


    @ApiModelProperty("departName")
    private String departName;


    @ApiModelProperty("synced")
    private Boolean synced;






}
