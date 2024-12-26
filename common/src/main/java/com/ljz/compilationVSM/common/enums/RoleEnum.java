package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色权限枚举
 *
 * @author 劳金赞
 * @since 2024-12-24
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    /**
     * 学生
     */
    STUDENT("student", "1"),

    /**
     * 教师
     */
    TEACHER("teacher", "2"),

    /**
     * 管理员
     */
    ADMIN("admin", "3");

    private final String name;
    private final String code;

    /**
     * 根据code获取枚举
     *
     * @param code 角色code
     * @return 角色枚举
     */
    public static RoleEnum getByCode(String code) {
        for (RoleEnum value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
