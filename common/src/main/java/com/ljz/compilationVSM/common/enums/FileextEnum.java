package com.ljz.compilationVSM.common.enums;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public enum FileextEnum {
    C("c"),
    CPP("cpp"),
    JAVA("java"),
    PYTHON("py"),
    GOLANG("go");



    private final String name;

    FileextEnum(String name) {
        this.name = name;
    }
    public static FileextEnum getByName(String name) {
        for (FileextEnum value : values()) {
            if (StringUtils.equals(value.getName(), name)) {
                return value;
            }
        }
        return null;
    }

}
