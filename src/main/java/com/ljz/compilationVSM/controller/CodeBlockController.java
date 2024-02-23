package com.ljz.compilationVSM.controller;


import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.service.CodeBlockService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/master/question")
public class CodeBlockController {

    @Autowired
    private CodeBlockService codeBlockService;

    @GetMapping
    public Result getComment(
            @RequestParam(value = "language") String language,
            @RequestParam(value = "compLanguage") String compLanguage,
            @RequestParam(value = "method") String method,
            HttpServletRequest request){
        String[] comments=codeBlockService.getComment(language,compLanguage,method,request.getHeader("token"));
        return Result.success(comments);
    }
}
