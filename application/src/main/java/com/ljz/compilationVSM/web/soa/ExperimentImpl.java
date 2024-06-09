package com.ljz.compilationVSM.web.soa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@RequestMapping("/experiment")
public class ExperimentImpl {

    @Autowired
    private ExperimentService experimentService;
    @PostMapping
    public Result checkExperiment(@RequestBody CodeDto codeDto){
        String feedback = experimentService.checkExperiment(codeDto);
        log.info(feedback);
        return Result.success(feedback);
    }
}
