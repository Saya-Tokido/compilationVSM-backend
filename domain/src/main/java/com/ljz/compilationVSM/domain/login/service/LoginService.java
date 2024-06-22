package com.ljz.compilationVSM.domain.login.service;

import com.ljz.compilationVSM.domain.login.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.login.dto.LoginDTO;

public interface LoginService {
    public LoggedDTO login(LoginDTO loginDTO);
    public String logout();
}
