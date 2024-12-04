package com.ljz.compilationVSM.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 编程语言枚举
 *
 * @author ljz
 * @since 2024-12-04
 */
@Deprecated
@Getter
public enum LanguageEnum {
    C("C","7"),
    CPP("C++","7"),
    JAVA("Java","8"),
    PYTHON("Python","15"),
    GOLANG("Golang","6");



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