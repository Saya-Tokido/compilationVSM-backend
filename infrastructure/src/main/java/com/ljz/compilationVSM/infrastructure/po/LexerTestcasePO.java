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
 * 词法分析器用例表
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 10:09:05
 */
@Getter
@Setter
@TableName("t_lexer_testcase")
public class LexerTestcasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 编译器id
     */
    @TableField("lexer_id")
    private Long lexerId;

    /**
     * 终端输入
     */
    @TableField("terminal_input")
    private String terminalInput;

    /**
     * 终端输出
     */
    @TableField("terminal_output")
    private String terminalOutput;

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
