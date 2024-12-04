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
 * 词法分析器题目表
 * </p>
 *
 * @author ljz
 * @since 2024-12-04 16:12:22
 */
@Getter
@Setter
@TableName("t_lexer")
public class LexerPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分词器id
     */
    @TableId("id")
    private Long id;

    /**
     * 编程语言
     */
    @TableField("language")
    private String language;

    /**
     * 待编译语言
     */
    @TableField("comp_language")
    private String compLanguage;

    /**
     * 题目描述
     */
    @TableField("description")
    private String description;

    /**
     * 提交次数
     */
    @TableField("commit_num")
    private Long commitNum;

    /**
     * 通过次数
     */
    @TableField("pass_num")
    private Long passNum;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_delete")
    private Boolean isDelete;
}
