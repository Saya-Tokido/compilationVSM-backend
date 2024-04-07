package com.ljz.compilationVSM.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    private Long id;
    private String type;
    @TableField(fill= FieldFill.INSERT)
    private LocalDateTime time;
    private String details;
    private Integer userId;
}
