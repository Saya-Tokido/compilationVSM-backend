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
 * 教师表
 * </p>
 *
 * @author ljz
 * @since 2025-01-19 19:46:28
 */
@Getter
@Setter
@TableName("t_teacher")
public class TeacherPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId("id")
    private Long id;

    /**
     * 账号id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 工号
     */
    @TableField("number")
    private Long number;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 所带教学班列表
     */
    @TableField("class_list")
    private String classList;

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
