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
 * 词法分析器测试用例表
 * </p>
 *
 * @author ljz
 * @since 2024-12-04 16:15:08
 */
@Getter
@Setter
@TableName("t_lexer_testcase")
public class LexerTestcasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用例id
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

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_delete")
    private Boolean isDelete;
}
