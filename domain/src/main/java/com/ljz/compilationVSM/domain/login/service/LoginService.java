package com.ljz.compilationVSM.domain.service;

import com.ljz.compilationVSM.domain.dto.LoggedDTO;
import com.ljz.compilationVSM.domain.dto.LoginDTO;

public interface LoginService {
    public LoggedDTO login(LoginDTO loginDTO);
    public String logout();
}
