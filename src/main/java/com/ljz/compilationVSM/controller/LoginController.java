package com.ljz.compilationVSM.controller;

import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.entity.User;
import com.ljz.compilationVSM.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j

public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("Get the login request");
        String token=loginService.login(user);
        return Result.success(token);
    }

    @GetMapping("/logout")
    public Result logout(){
        loginService.logout();
        return Result.success("Logged out!");
    }
}
