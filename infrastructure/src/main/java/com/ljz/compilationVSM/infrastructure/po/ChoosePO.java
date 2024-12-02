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
 * @since 2024-12-02 15:12:10
 */
@Getter
@Setter
@TableName("t_choose")
public class ChoosePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("title")
    private String title;

    @TableField("choice0")
    private String choice0;

    @TableField("choice1")
    private String choice1;

    @TableField("choice2")
    private String choice2;

    @TableField("choice3")
    private String choice3;

    @TableField("key_answer")
    private String keyAnswer;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_delete")
    private Boolean isDelete;
}
