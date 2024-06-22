package com.ljz.compilationVSM.web.security.filter;

import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.domain.login.dto.LoginUserDTO;
import com.ljz.compilationVSM.domain.login.service.AuthenticationService;
import com.ljz.compilationVSM.domain.utils.TokenHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    private final AuthenticationService authenticationService;
    @Autowired
    public JwtAuthenticationTokenFilter(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, BizException {

        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        Long userId;
        try {
            userId= TokenHandler.parseToken(token);
        } catch (Exception e) {
            throw new BizException(1003,"token error!");
        }
        LoginUserDTO loginUser=authenticationService.getLoginUserDTO(userId.toString());

        if(Objects.isNull(loginUser)){
            throw new BizException(1004,"User is not logged in!");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser.getAuthorities(), loginUser, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}