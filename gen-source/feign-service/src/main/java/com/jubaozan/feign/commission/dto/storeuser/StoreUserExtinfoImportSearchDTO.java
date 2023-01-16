/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.storeuser.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * StoreUserExtinfoImport
 * @version 1.0
 * @author jjw
 */
@Data
public class StoreUserExtinfoImportSearchDTO extends StoreUserExtinfoImportSearchRequest  implements Serializable {

    private String searchKey;

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

        @ApiModelProperty("createAt开始")
        private LocalDateTime createAtStart;

        @ApiModelProperty("createAt结束")
        private LocalDateTime createAtEnd;

        @ApiModelProperty("syncedAt开始")
        private LocalDateTime syncedAtStart;

        @ApiModelProperty("syncedAt结束")
        private LocalDateTime syncedAtEnd;

    Sort sort;
@NotNull(message = "pageSize不能为空")
    Integer pageSize;
@NotNull(message = "pagepageNo不能为空")
    Integer pageNo;


}
//