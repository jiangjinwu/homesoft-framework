package com.jubaozan.service.commission.domain.storeuser.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.github.meixuesong.aggregatepersistence.Versionable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: jiangjinwu
 * @Date: 2020/12/4
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="GradeRelation对象")
public class GradeRelation implements Versionable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long storeId;

    private Long userId;


    private Long p1;

    private Long p2;

    private Long p3;

    private Long p4;

    private Long p5;

    private Long levelId;



    private LocalDateTime createAt;

    @ApiModelProperty(value = "timestamp 报错")
    private LocalDateTime upgradeTime;

    @ApiModelProperty(value = "说明")
    private String remark;

    @ApiModelProperty(value = "上一级levelid")
    private Long p1LevelId;

    @ApiModelProperty(value = "推荐人id")
    private Long refereeId;

    int version;
}
