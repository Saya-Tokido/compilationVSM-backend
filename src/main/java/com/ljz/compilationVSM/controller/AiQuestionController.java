package com.ljz.compilationVSM.controller;


import com.ljz.compilationVSM.api.requst.FreeQARequest;
import com.ljz.compilationVSM.api.requst.OptimRequest;
import com.ljz.compilationVSM.api.requst.SpecificQuestionRequest;
import com.ljz.compilationVSM.api.response.AiQAQuestionListResponse;
import com.ljz.compilationVSM.api.response.FreeQAResponse;
import com.ljz.compilationVSM.api.response.OptimResponse;
import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.domain.convert.OptimCodeMapping;
import com.ljz.compilationVSM.domain.dto.OptimizedDTO;
import com.ljz.compilationVSM.domain.task.AiQAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ai 问答
 */
@RestController
@Slf4j
@RequestMapping("/aiqa")
public class AiQuestionController {

    @Autowired
    private AiQAService aiQAService;
    @Autowired
    private OptimCodeMapping optimCodeMapping;

    /**
     * 代码优化
     *
     * @param request
     * @return
     */
    @PostMapping("/optim")
    public Result<OptimResponse> optimize(@RequestBody OptimRequest request) {
        OptimizedDTO optimize = aiQAService.optimize(optimCodeMapping.convert(request));
        return Result.success(optimCodeMapping.convert2(optimize));
    }

    /**
     * 自由问答
     *
     * @param request
     * @return
     */
    @PostMapping("/knowledge/free")
    public Result<FreeQAResponse> askByMessage(@RequestBody FreeQARequest request) {
        return Result.success(aiQAService.askByMessage(request.getQuestion()));
    }

    /**
     * 获取指定问答的问题列表
     * @return
     */
    @GetMapping("/knowledge/questionlist")
    public Result<AiQAQuestionListResponse> getQuestionList(){
        return Result.success(aiQAService.getQuestionList());
    }

    /**
     * 指定问题问答
     *
     * @param request
     * @return
     */
    @PostMapping("/knowledge/specific_question")
    public Result<Object> askByKey(@RequestBody SpecificQuestionRequest request) {
        return Result.success(aiQAService.askByKey(request.getKey()));
    }
}
