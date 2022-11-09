package com.jubaozan.service.commission.domain.storeuser.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.github.meixuesong.aggregatepersistence.Versionable;
import com.jubaozan.service.commission.domain.storeuser.assembler.StoreUserAssembler;
import com.jubaozan.service.commission.domain.storeuser.domain.StoreUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Base64;


/**
 * @Author: jiangjinwu
 * @Date: 2021/2/27
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_store_user")
@ApiModel(value="StoreUser对象")
public class StoreUserDO implements Versionable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自增ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "门店ID")
    private Long storeId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "权重")
    private Integer weight;


    @ApiModelProperty(value = "推荐人ID")
    private Long refereeId;

    @ApiModelProperty(value = "上级id")
    private Long parentId;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    /*@ApiModelProperty(value = "等级ID")
    private Long levelId;

    @ApiModelProperty(value = "昵称")
    private String nick;

    @ApiModelProperty(value = "姓名")
    private String name;



    @ApiModelProperty(value = "头像")
    private String avatar;

**/


    /**


    @ApiModelProperty(value = "会员等级ID")
    private Long memberLevelId;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createAt;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateAt;

    @ApiModelProperty(value = "修改推荐人次数")
    private Integer modRefCnt;*/

    @ApiModelProperty(value = "首次升级时间")
    private LocalDateTime grdChangeTime;

//    @ApiModelProperty("最后级别变更时间")
//    LocalDateTime levelChangeTime;

//    @TableField(exist = false)
//    private Integer refereeIndex;
//
//    @ApiModelProperty("是否锁定推荐关系")
//    Boolean refRelIsfix;

    @ApiModelProperty("推荐关系锁定过期时间")
    LocalDateTime refRelExpireTime;

//    Boolean yeePayVerified;

//    public String getNameOrNick(){
//        return StringUtils.isNotEmpty(this.getName())?this.getName():this.getNick();
//    }

//    @JsonIgnore
//    public boolean isPlatform(){
//        return this.getWeight().equals(CommissionWeightEnum.PLATFORM.getValue());
//    }

    public static void main(String[] args) {
        System.out.println(new String(Base64.getEncoder().encode("meinianhaowu".getBytes())));
    }

    @Override
    public int getVersion() {
        return 0;
    }




    public StoreUser toStoreUser(){
        return StoreUserAssembler.INSTANCE.do2Domain(this);
    }


}
