package com.ljz.compilationVSM.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Data;

@Data
public class MethodName {
    private Integer id;
    private String language;
    private String compLanguage;
    private String name;
    @EnumValue
    private Level level;
    private Long commitNum;
    private Long passNum;

    public enum Level implements IEnum<Short> {
        EASY((short)0,"easy"),
        MEDIUM((short)1,"medium"),
        HARD((short)2,"hard");

        @EnumValue
        private final Short value;
        private String str;

        Level(short value,String str) {
            this.value = value;
            this.str = str;
        }
        public Short getValue() {
            return value;
        }
        public String getStr() {
            return str;
        }
    }
}
