package com.jubaozan.service.commission.domain.storeuser.dao;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("t_grade_relation")
@ApiModel(value="GradeRelation对象")
public class GradeRelationDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long storeId;

    private Long userId;


    @TableField(updateStrategy= FieldStrategy.IGNORED, jdbcType= JdbcType.BIGINT)
    private Long p1;
    @TableField(updateStrategy=FieldStrategy.IGNORED, jdbcType= JdbcType.BIGINT)
    private Long p2;
    @TableField(updateStrategy=FieldStrategy.IGNORED, jdbcType= JdbcType.BIGINT)
    private Long p3;
    @TableField(updateStrategy=FieldStrategy.IGNORED, jdbcType= JdbcType.BIGINT)
    private Long p4;
    @TableField(updateStrategy=FieldStrategy.IGNORED, jdbcType= JdbcType.BIGINT)
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

}
