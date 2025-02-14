package com.ljz.compilationVSM.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 词法分析器代码查重表,Plagiarism detection
 * </p>
 *
 * @author ljz
 * @since 2025-02-11 20:35:31
 */
@Getter
@Setter
@TableName("t_lexer_p_d")
public class LexerPDPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 词法分析器题目id
     */
    @TableField("lexer_id")
    private Long lexerId;

    /**
     * 查重代码id
     */
    @TableField("plagiarism_code_id")
    private Long plagiarismCodeId;

    /**
     * 参照代码id
     */
    @TableField("comp_code_id")
    private Long compCodeId;

    /**
     * 查重率,百分比
     */
    @TableField("rate")
    private BigDecimal rate;

    /**
     * 查重教学班
     */
    @TableField("teach_class")
    private String teachClass;

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
