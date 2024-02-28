package com.ljz.compilationVSM.service;

import com.ljz.compilationVSM.entity.User;

public interface LoginService {
    public String login(User user);
    public void logout();
}
