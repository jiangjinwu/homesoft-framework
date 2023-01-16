/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.storeuser.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;

/**
 * StoreUserExtinfoImport
 * @version 1.0
 * @author jjw
 * @date {DATA}
 */
@Data
public class StoreUserExtinfoImportVO implements Serializable {

    // id
     @ApiModelProperty("id")
    private Long id;
    // userId
     @ApiModelProperty("userId")
    private Long userId;
    // importId
     @ApiModelProperty("importId")
    private Long importId;
    // storeId
     @ApiModelProperty("storeId")
    private Long storeId;
    // workNo
     @ApiModelProperty("workNo")
    private String workNo;
    // mobile
     @ApiModelProperty("mobile")
    private String mobile;
    // departName
     @ApiModelProperty("departName")
    private String departName;
    // synced
     @ApiModelProperty("synced")
    private Boolean synced;
    // createAt
     @ApiModelProperty("createAt")
    private LocalDateTime createAt;
    // syncedAt
     @ApiModelProperty("syncedAt")
    private LocalDateTime syncedAt;
}
