package com.ljz.compilationVSM.domain.service;

import com.ljz.compilationVSM.domain.dto.LoginUserDTO;

public interface AuthenticationService {

    public LoginUserDTO getLoginUserDTO(String userId);
}
