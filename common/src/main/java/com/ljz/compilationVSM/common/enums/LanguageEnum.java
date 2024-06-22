package com.ljz.compilationVSM.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum LanguageEnum {
    C("C","7"),
    CPP("C++","8"),
    JAVA("Java","9"),
    PYTHON("Python","10"),
    GOLANG("Golang","11");



    private final String name;
    private final String code;

    LanguageEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }
    public static LanguageEnum getByName(String name) {
        for (LanguageEnum value : values()) {
            if (StringUtils.equals(value.getName(), name)) {
                return value;
            }
        }
        return null;
    }

}