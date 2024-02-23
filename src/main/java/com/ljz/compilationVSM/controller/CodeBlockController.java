package com.ljz.compilationVSM.controller;


import com.ljz.compilationVSM.common.Result;
import com.ljz.compilationVSM.service.CodeBlockService;
import com.ljz.compilationVSM.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/master/question")
public class CodeBlockController {

    @Autowired
    private CodeBlockService codeBlockService;
    @Autowired
    private UserService userService;

    @GetMapping
    public Result getComment(
            @RequestParam(value = "language") String language,
            @RequestParam(value = "compLanguage") String compLanguage,
            @RequestParam(value = "method") String method,
            HttpServletRequest request){
        userService.logged(request.getHeader("token"));
        String[] comments= codeBlockService.getComment(language,compLanguage,method);
        return Result.success(comments);
    }
}
