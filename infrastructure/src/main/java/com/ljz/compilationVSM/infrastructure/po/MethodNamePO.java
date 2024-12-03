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
 * @since 2024-12-02 21:28:15
 */
@Getter
@Setter
@TableName("t_method_name")
public class MethodNamePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("id")
    private Long id;

    @TableField("language")
    private String language;

    @TableField("comp_language")
    private String compLanguage;

    @TableField("name")
    private String name;

    @TableField("level")
    private String level;

    @TableField("commit_num")
    private Long commitNum;

    @TableField("pass_num")
    private Long passNum;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_delete")
    private Boolean isDelete;
}
