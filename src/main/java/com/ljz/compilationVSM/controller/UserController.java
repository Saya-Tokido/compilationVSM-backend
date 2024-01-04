package com.ljz.compilationVSM.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.entity.User;
import com.ljz.compilationVSM.service.UserService;
import com.ljz.compilationVSM.utils.TokenHandler;
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
    public Result<String> login(HttpServletRequest request, @RequestBody Map loginMap){
        log.info("Get the login request");
        HttpSession session=request.getSession();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,loginMap.get("userName"))
                .eq(User::getPassword,loginMap.get("password"));

        User user = userService.getOne(queryWrapper);
        if(user == null){

            log.info("login failed");
            return Result.error("userName or password error!");
        }
        log.info("login successful");
        return Result.success(TokenHandler.getToken(user));
    }


}
