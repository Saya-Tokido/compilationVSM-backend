package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Runcode编译接口 编程语言枚举
 *
 * @author ljz
 * @since 2024-12-04
 */
@Getter
@AllArgsConstructor
public enum LanguageEnum2 {
    C("C","c"),
    CPP("C++","cpp"),
    JAVA("Java","java"),
    PYTHON("Python3","python3"),
    GOLANG("Golang","go"),
    RUST("Rust","rust");



    private final String name;
    private final String type;

    public static LanguageEnum2 getByName(String name) {
        for (LanguageEnum2 value : values()) {
            if (StringUtils.equals(value.getName(), name)) {
                return value;
            }
        }
        return null;
    }

}