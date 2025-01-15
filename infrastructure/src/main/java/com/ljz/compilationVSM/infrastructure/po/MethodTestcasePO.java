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
 * 
 * </p>
 *
 * @author ljz
 * @since 2025-01-15 16:09:00
 */
@Getter
@Setter
@TableName("t_method_testcase")
public class MethodTestcasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用例id
     */
    @TableId("id")
    private Long id;

    /**
     * 函数id
     */
    @TableField("method_id")
    private Long methodId;

    /**
     * 校验时前置代码
     */
    @TableField("pre_code")
    private String preCode;

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
