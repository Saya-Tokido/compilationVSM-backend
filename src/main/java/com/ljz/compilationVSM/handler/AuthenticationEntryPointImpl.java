package com.ljz.compilationVSM.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws JsonProcessingException {
        Result result = Result.error(HttpStatus.UNAUTHORIZED.value(),"Authentication failed,please log in again!");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(result);
        WebUtils.renderString(response,jsonString);
    }
}
