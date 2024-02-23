package com.ljz.compilationVSM.service;

import com.ljz.compilationVSM.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface UserService extends IService<User>  {
    public String login(Map loginMap);
    public void logged(String token);
}
