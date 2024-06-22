package com.ljz.compilationVSM.domain.login.service;

import com.ljz.compilationVSM.domain.login.dto.LoginUserDTO;

public interface AuthenticationService {

    public LoginUserDTO getLoginUserDTO(String userId);
}
