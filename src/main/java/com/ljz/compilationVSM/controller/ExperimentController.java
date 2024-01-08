package com.ljz.compilationVSM.controller;

import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.dto.CodeDto;
import com.ljz.compilationVSM.service.ExperimentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/experiment")
public class ExperimentController {

    @Autowired
    private ExperimentService experimentService;
    @PostMapping
    public Result checkExperiment(@RequestBody CodeDto codeDto){
        String feedback = experimentService.checkExperiment(codeDto);
        return Result.success(feedback);
    }
}
