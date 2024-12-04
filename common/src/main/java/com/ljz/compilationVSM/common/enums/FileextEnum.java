package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
@Deprecated
public enum FileextEnum {
    C("C","c"),
    CPP("C++","cpp"),
    JAVA("Java","java"),
    PYTHON("Python","py3"),
    GOLANG("Golang","go");



    private final String name;
    private final String ext;

    public static FileextEnum getByName(String name) {
        for (FileextEnum value : values()) {
            if (StringUtils.equals(value.getName(), name)) {
                return value;
            }
        }
        return null;
    }

    public static FileextEnum getByExt(String ext) {
        for (FileextEnum value : values()) {
            if (StringUtils.equals(value.getExt(), ext)) {
                return value;
            }
        }
        return null;
    }

}
