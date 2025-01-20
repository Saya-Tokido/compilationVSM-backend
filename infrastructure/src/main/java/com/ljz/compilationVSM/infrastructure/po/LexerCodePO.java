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
 * 词法分析器代码表
 * </p>
 *
 * @author ljz
 * @since 2025-01-19 19:46:28
 */
@Getter
@Setter
@TableName("t_lexer_code")
public class LexerCodePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 完整代码
     */
    @TableField("code")
    private String code;

    /**
     * 查重代码映射
     */
    @TableField("code_map")
    private String codeMap;

    /**
     * 代码行数
     */
    @TableField("row_num")
    private Integer rowNum;

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
