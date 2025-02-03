package com.ljz.compilationVSM.web.soa.common;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.common.LoginIface;
import com.ljz.compilationVSM.api.request.common.LoginRequest;
import com.ljz.compilationVSM.api.response.common.LoginResponse;
import com.ljz.compilationVSM.domain.login.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.login.service.LoginService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.common.LoginMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 登录接口实现
 *
 * @author ljz
 * @since 2024-12-25
 */
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/api")
public class LoginImpl implements LoginIface {

    private final LoginService loginService;
    private final LoginMapping loginMapping;

    @PostMapping("/login")
    @Override
    public Response<LoginResponse> login(@RequestBody LoginRequest request){
        LoggedDTO logged = loginService.login(loginMapping.convert(request));
        return Response.success(loginMapping.convert2(logged));
    }

    @GetMapping("/logout")
    @Override
    @UserAuth
    public Response<String> logout(){
        loginService.logout();
        return Response.success("登出成功!");
    }
}
