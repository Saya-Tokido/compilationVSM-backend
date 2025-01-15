package com.ljz.compilationVSM.web.config.aspect;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.common.constant.Constant;
import com.ljz.compilationVSM.common.dto.LoginUserDTO;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.common.enums.RoleEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.property.RoleConfig;
import com.ljz.compilationVSM.common.utils.JsonUtil;
import com.ljz.compilationVSM.common.utils.RedisUtil;
import com.ljz.compilationVSM.common.utils.TokenHandler;
import com.ljz.compilationVSM.common.utils.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * 全局异常处理业务层切面
 */
@Order(-1)
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessResultAspect {

    private final RedisUtil redisUtil;

    @Value("${redis-key-prefix.login-info}")
    private String loginInfoPrefix;

    private final RoleConfig roleConfig;


    /**
     * 切入点
     */
    @Pointcut("execution(public * com.ljz.compilationVSM.web.soa..*(..))")
    private void commonPointcut() {
    }

    /**
     * 应用层环绕通知处理
     *
     * @param joinPoint 连接点
     * @return 处理结果
     * @throws Throwable 无法处理的异常
     */
    @Around("commonPointcut()")
    public Object cutAllAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            userAuth(joinPoint);
            result = joinPoint.proceed();
//        } catch (ConstraintViolationException e) {
//            String methodName = joinPoint.getSignature().getName();
//            String className = joinPoint.getTarget().getClass().getSimpleName();
//            String collect = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));
//            log.error("接口执行异常，执行类:{}, 执行方法:{}, 入参:{}", methodName, className, JSONUtil.toJsonStr(joinPoint.getArgs()), e);
//            result = Response.failed(AssetErrorCode.PARAM_ERROR.getCode(), collect);
        } catch (Exception ex) {
            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            log.error("接口执行异常，执行类:{}, 执行方法:{}, 入参:{}", className, methodName, JsonUtil.toJsonStr(joinPoint.getArgs()), ex);
            result = handleException(ex);
        } finally {
            UserContextHolder.clear();
        }
        return result;
    }

    public <T> Response<T> handleException(Exception ex) {
        Response<T> result;
        if (ex instanceof BizException bizException) {
            result = Response.error(bizException.getErrorInfo(), bizException.getData());
        } else {
            result = Response.error(BizExceptionCodeEnum.SERVER_ERROR);
        }
        return result;
    }

    /**
     * 用户认证和权限校验
     *
     * @param point 连接点
     */
    private void userAuth(JoinPoint point) {
        if (Objects.isNull(point)) {
            return;
        }
        // 获取当前配置的注解信息
        Class<?> currClass = point.getTarget().getClass();
        // 获取类上的注解信息
        UserAuth annotationInClass = currClass.getAnnotation(UserAuth.class);
        // 获取方法上的注解信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取被调用方法
        Method method = signature.getMethod();
        UserAuth annotationInMethod = method.getAnnotation(UserAuth.class);

        Integer role = checkLoginAuth(point, annotationInClass, annotationInMethod);
        if (Objects.nonNull(role)) {
            checkFunctionPermission(role, annotationInClass, annotationInMethod);
        }
    }

    /**
     * 用户认证校验
     *
     * @param point              连接点
     * @param annotationInClass  类上注解
     * @param annotationInMethod 方法上注解
     * @return role, 返回null即为不需要校验和授权
     */
    private Integer checkLoginAuth(JoinPoint point, UserAuth annotationInClass, UserAuth annotationInMethod) {
        // 方法上的注解配置优先级更高
        if (Objects.nonNull(annotationInMethod)) {
            boolean b = annotationInMethod.loginAuth();
            if (!b) {
                return null;
            }
        } else {
            if (Objects.nonNull(annotationInClass)) {
                boolean b = annotationInClass.loginAuth();
                if (!b) {
                    return null;
                }
            } else {
                //  没有注解不进行校验
                return null;
            }
        }

        // 获取登录信息存入 ThreadLocal
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes attributes) {
            // 获取token
            Optional<Object> tokenOptional = Optional.ofNullable(attributes.getRequest().getHeader(Constant.TOKEN));
            Long loginId;
            if (tokenOptional.isPresent()) {
                try {
                    loginId = TokenHandler.parseToken((String) tokenOptional.get());
                } catch (Exception exception) {
                    log.info("非法token");
                    throw new BizException(BizExceptionCodeEnum.ILLEGAL_TOKEN_ERROR);
                }
            } else {
                log.info("token 为空");
                throw new BizException(BizExceptionCodeEnum.NULL_TOKEN_ERROR);
            }
            // 获取登录信息并存储
            LoginUserDTO loginUserDTO = redisUtil.loginUserGet(loginInfoPrefix + loginId);
            if (Objects.isNull(loginUserDTO)) {
                log.info("登录过期");
                throw new BizException(BizExceptionCodeEnum.LOGIN_EXPIRED_ERROR);
            }
            UserContextHolder.setUser(loginUserDTO);
            return UserContextHolder.getRole();
        }
        log.error("错误！无法获取请求头信息");
        throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);

    }

    /**
     * 权限校验
     *
     * @param role               用户角色
     * @param annotationInClass  类上注解信息
     * @param annotationInMethod 方法上注解信息
     */
    private void checkFunctionPermission(Integer role, UserAuth annotationInClass, UserAuth annotationInMethod) {
        // 获取需要校验的权限
        if (Objects.isNull(annotationInClass) && Objects.isNull(annotationInMethod)) {
            return;
        }
        PermissionEnum permission = PermissionEnum.NULL;
        if (Objects.nonNull(annotationInMethod)) {
            permission = annotationInMethod.permission();
        }
        // 方法上权限优先级更高
        if (PermissionEnum.NULL.equals(permission) && annotationInClass != null) {
            permission = annotationInClass.permission();
        }
        if (PermissionEnum.NULL.equals(permission)) {
            return;
        }

        // 权限校验
        boolean hasPermission = roleConfig.hasPermission(Objects.requireNonNull(RoleEnum.getByCode(String.valueOf(role))).getName(), permission.getCode());
        if (!hasPermission) {
            log.info("用户 userId = {} 无权限访问 {}", UserContextHolder.getUserId(), permission.getName());
            throw new BizException(BizExceptionCodeEnum.PERMISSION_FORBIDDEN_ERROR);
        }
    }
}