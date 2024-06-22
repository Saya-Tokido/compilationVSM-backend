package com.ljz.compilationVSM.web.soa;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.LoginIface;
import com.ljz.compilationVSM.api.request.LoginRequest;
import com.ljz.compilationVSM.api.response.LoginResponse;
import com.ljz.compilationVSM.domain.login.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.login.service.LoginService;
import com.ljz.compilationVSM.web.convert.LoginMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j

public class LoginImpl implements LoginIface {

    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginMapping loginMapping;

    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody LoginRequest request){
        log.info("Get the login request");
        LoggedDTO logged = loginService.login(loginMapping.convert(request));
        return Response.success(loginMapping.convert2(logged));
    }

    @GetMapping("/logout")
    public Response<String> logout(){
        String message = loginService.logout();
        return Response.success(message);
    }
}
