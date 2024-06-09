package com.ljz.compilationVSM.api.iface;


import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.LoginRequest;
import com.ljz.compilationVSM.api.response.LoginResponse;

/**
 * 登录登出接口
 */
public interface LoginIface {

    public Response<LoginResponse> login(LoginRequest request);

    public Response<String> logout();
}
