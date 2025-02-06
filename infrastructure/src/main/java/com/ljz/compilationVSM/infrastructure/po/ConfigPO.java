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
 * 配置表
 * </p>
 *
 * @author ljz
 * @since 2025-02-06 15:16:26
 */
@Getter
@Setter
@TableName("t_config")
public class ConfigPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 词法分析器题截止日期
     */
    @TableField("lexer_deadline")
    private LocalDateTime lexerDeadline;

    /**
     * 计分的词法分析器题id
     */
    @TableField("lexer_id")
    private Long lexerId;

    /**
     * 最后更新人id
     */
    @TableField("last_update_user_id")
    private Long lastUpdateUserId;

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
