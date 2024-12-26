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
 * 客观题答题情况表
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 10:09:05
 */
@Getter
@Setter
@TableName("t_obj_answer")
public class ObjAnswerPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 学生id
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 选择题id列表
     */
    @TableField("choose_id_list")
    private String chooseIdList;

    /**
     * 填空题id列表
     */
    @TableField("fill_id_list")
    private String fillIdList;

    /**
     * 选择题答题选项列表
     */
    @TableField("choose_answer_list")
    private String chooseAnswerList;

    /**
     * 填空题答题内容列表
     */
    @TableField("fill_answer_list")
    private String fillAnswerList;

    /**
     * 选择题成绩
     */
    @TableField("choose_grade")
    private Integer chooseGrade;

    /**
     * 填空题成绩
     */
    @TableField("fill_grade")
    private Integer fillGrade;

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
