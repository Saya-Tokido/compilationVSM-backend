package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum LevelEnum {
    EASY("easy","0"),
    MEDIUM("medium","1"),
    HARD("hard","2");

    private final String name;
    private final String code;

    public static LevelEnum getByCode(String code){
        for (LevelEnum value : values()) {
            if (StringUtils.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }
}
