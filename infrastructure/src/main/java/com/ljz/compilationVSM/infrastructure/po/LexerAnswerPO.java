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
 * 词法分析器题成绩表
 * </p>
 *
 * @author ljz
 * @since 2025-01-15 16:09:00
 */
@Getter
@Setter
@TableName("t_lexer_answer")
public class LexerAnswerPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 学生用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 最后一次提交的代码id
     */
    @TableField("last_code_id")
    private Long lastCodeId;

    /**
     * 有得分的最高成绩的代码id
     */
    @TableField("best_code_id")
    private Long bestCodeId;

    /**
     * 成绩
     */
    @TableField("grade")
    private Integer grade;

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
