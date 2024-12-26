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
 * 选择题表
 * </p>
 *
 * @author ljz
 * @since 2024-12-25 10:09:05
 */
@Getter
@Setter
@TableName("t_choose")
public class ChoosePO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 题目
     */
    @TableField("title")
    private String title;

    /**
     * 选项0
     */
    @TableField("choice0")
    private String choice0;

    /**
     * 选项1
     */
    @TableField("choice1")
    private String choice1;

    /**
     * 选项2
     */
    @TableField("choice2")
    private String choice2;

    /**
     * 选项3
     */
    @TableField("choice3")
    private String choice3;

    /**
     * 正确选项内容
     */
    @TableField("key_answer")
    private String keyAnswer;

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
