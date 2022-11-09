/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package commission.service.commission.model;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * StoreDepartmentConfig
 * @version 1.0
 * @author jjw
 */
@Data
public class StoreDepartmentConfigSearchRequest extends BaseSearchDTO  implements Serializable {

    private String searchKey;

        @ApiModelProperty("taxRate")
        private Long taxRate;

        @ApiModelProperty("orgid")
        private Long orgid;

        @ApiModelProperty("orgType")
        private Integer orgType;

        @ApiModelProperty("创建时间开始")
        private LocalDateTime createAtStart;

        @ApiModelProperty("创建时间结束")
        private LocalDateTime createAtEnd;

        @ApiModelProperty("createUser")
        private Long createUser;

        @ApiModelProperty("updateAt开始")
        private LocalDateTime updateAtStart;

        @ApiModelProperty("updateAt结束")
        private LocalDateTime updateAtEnd;

        @ApiModelProperty("updateUser")
        private Long updateUser;

        @ApiModelProperty("remark")
        private String remark;

    Sort sort;
@NotNull(message = "pageSize不能为空")
    Integer pageSize;
@NotNull(message = "pagepageNo不能为空")
    Integer pageNo;


}
//