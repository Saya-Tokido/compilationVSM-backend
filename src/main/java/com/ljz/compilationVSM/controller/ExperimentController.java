package com.ljz.compilationVSM.controller;

import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.dto.CodeDto;
import com.ljz.compilationVSM.service.ExperimentService;
import com.ljz.compilationVSM.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@RequestMapping("/experiment")
public class ExperimentController {

    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private UserService userService;
    @PostMapping
    public Result checkExperiment(@RequestBody CodeDto codeDto, HttpServletRequest request){
        userService.logged(request.getHeader("token"));
        String feedback = experimentService.checkExperiment(codeDto);
        log.info(feedback);
        return Result.success(feedback);
    }
}
