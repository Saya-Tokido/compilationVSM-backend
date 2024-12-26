package com.ljz.compilationVSM.common.property;

import com.ljz.compilationVSM.common.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class RoleConfig {

    private final Map<String,Set<String>> rolePermissionMap;

    /**
     * 初始化角色权限映射
     *
     * @param permission 配置中的角色权限字符串
     */
    public RoleConfig(@Value("${role-permission}") String permission) {
        rolePermissionMap = JsonUtil.toMap(permission);
    }

    /**
     * 查询角色是否存在指定权限
     *
     * @param role 角色
     * @param permission 权限
     * @return 查询结果
     */
    public boolean hasPermission(String role, String permission){
        return rolePermissionMap.get(role).contains(permission);
    }

}
