package com.ljz.compilationVSM.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljz.compilationVSM.dao.UserMapper;
import com.ljz.compilationVSM.entity.User;
import com.ljz.compilationVSM.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
