package com.ljz.compilationVSM.api.iface.common;


import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.common.LoginRequest;
import com.ljz.compilationVSM.api.response.common.LoginResponse;

/**
 * 登录登出接口
 *
 * @author ljz
 * @since 2024-12-05
 */
public interface LoginIface {

    /**
     * 登录
     * @param request 登录请求
     * @return 登录响应
     */
    Response<LoginResponse> login(LoginRequest request);

    /**
     * 登出
     * @return 登出信息
     */
    Response<String> logout();
}
