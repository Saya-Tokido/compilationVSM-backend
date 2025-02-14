package com.ljz.compilationVSM.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 学生表
 * </p>
 *
 * @author ljz
 * @since 2025-02-07 12:59:40
 */
@Getter
@Setter
@TableName("t_student")
public class StudentPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 账号id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 学号
     */
    @TableField("number")
    private Long number;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 行政班
     */
    @TableField("admin_class")
    private String adminClass;

    /**
     * 教学班
     */
    @TableField("teach_class")
    private String teachClass;

    /**
     * 客观题成绩
     */
    @TableField("obj_score")
    private Integer objScore;

    /**
     * 函数题成绩
     */
    @TableField("method_score")
    private Integer methodScore;

    /**
     * 词法分析题成绩
     */
    @TableField("lexer_score")
    private Integer lexerScore;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标志，0为未删除，1为删除
     */
    @TableField("is_delete")
    private Boolean isDelete;
}
