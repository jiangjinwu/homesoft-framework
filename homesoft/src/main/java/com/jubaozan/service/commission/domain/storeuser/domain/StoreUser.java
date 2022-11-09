package com.jubaozan.service.commission.domain.storeuser.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.meixuesong.aggregatepersistence.Versionable;
import com.jubaozan.service.commission.level.StoreUserWeightEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;


/**
 * @Author: jiangjinwu
 * @Date: 2021/2/27
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class StoreUser implements Versionable {
    private static final long serialVersionUID = 1L;

    GradeRelation gradeRelation;

    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "门店ID")
    private Long storeId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "等级ID")
    private Long levelId;

    @ApiModelProperty(value = "昵称")
    private String nick;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "推荐人ID")
    private Long refereeId;

    @ApiModelProperty(value = "权重")
    private Integer weight;

    @ApiModelProperty(value = "上级id")
    private Long parentId;

    @ApiModelProperty(value = "会员等级ID")
    private Long memberLevelId;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateAt;

    @ApiModelProperty(value = "修改推荐人次数")
    private Integer modRefCnt;

    @ApiModelProperty(value = "首次升级时间")
    private LocalDateTime grdChangeTime;

    @ApiModelProperty("最后级别变更时间")
    LocalDateTime levelChangeTime;

    @TableField(exist = false)
    private Integer refereeIndex;

    @ApiModelProperty("是否锁定推荐关系")
    Boolean refRelIsfix;

    @ApiModelProperty("推荐关系锁定过期时间")
    LocalDateTime refRelExpireTime;

    Boolean yeePayVerified;


    int version;

    public String getNameOrNick(){
        return StringUtils.isNotEmpty(this.getName())?this.getName():this.getNick();
    }

    @JsonIgnore
    public boolean isPlatform(){
        return this.getWeight().equals(StoreUserWeightEnum.PLATFORM.getValue());
    }


    public int getVersion() {
        return version;
    }
}
