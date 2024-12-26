package com.ljz.compilationVSM.web.config.aspect;

import com.ljz.compilationVSM.common.enums.PermissionEnum;

import java.lang.annotation.*;

/**
 * 不鉴权的接口注解
 *
 * @author ljz
 * @since 2024-12-25
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface UserAuth {

    /**
     * 是否进行用户token校验标识，默认需要校验
     */
    boolean loginAuth() default true;

    /**
     * 不为空权限，会查询 是否有 “permission” 权限
     */
    PermissionEnum permission() default PermissionEnum.NULL;
}
