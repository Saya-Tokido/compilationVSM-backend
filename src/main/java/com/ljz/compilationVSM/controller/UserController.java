package com.ljz.compilationVSM.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.entity.User;
import com.ljz.compilationVSM.service.UserService;
import com.ljz.compilationVSM.util.TokenHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j

@RequestMapping("/login")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public Result login(@RequestBody Map loginMap){
        log.info("Get the login request");
        String token=userService.login(loginMap);
        return Result.success(token);
    }
}
