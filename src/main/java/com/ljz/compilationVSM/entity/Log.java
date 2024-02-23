package com.ljz.compilationVSM.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Log {
    Long id;
    String type;
    @TableField(fill= FieldFill.INSERT)
    LocalDateTime time;
    String details;
    Integer userId;
}
