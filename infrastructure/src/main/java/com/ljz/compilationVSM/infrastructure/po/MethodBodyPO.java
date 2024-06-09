package com.ljz.compilationVSM.infrastructure.po;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-06-09 17:32:58
 */
@Getter
@Setter
@TableName("t_method_body")
public class MethodBodyPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("method_id")
    private Integer methodId;

    @TableField("description")
    private String description;

    @TableField("input")
    private String input;

    @TableField("output")
    private String output;

    @TableField("in_param")
    private String inParam;

    @TableField("out_param")
    private String outParam;

    @TableField("body")
    private String body;

    @TableField("check_body")
    private String checkBody;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_delete")
    private Byte isDelete;
}
