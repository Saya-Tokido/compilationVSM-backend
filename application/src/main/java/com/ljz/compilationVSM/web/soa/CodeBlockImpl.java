//package com.ljz.compilationVSM.web.soa;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@Slf4j
////@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@RequestMapping("/master/question")
//public class CodeBlockImpl {
//
//    @Autowired
//    private CodeBlockService codeBlockService;
//
//    @GetMapping("/method_impl")
//    public Result getComment(
//            @RequestParam(value = "language") String language,
//            @RequestParam(value = "compLanguage") String compLanguage,
//            @RequestParam(value = "method") String method) {
//        String[] comments = codeBlockService.getComment(language, compLanguage, method);
//        return Result.success(comments);
//    }
//
//    @GetMapping("/method_name")
//    public Result getMethodName(
//            @RequestParam(value = "language") String language,
//            @RequestParam(value = "compLanguage") String compLanguage) {
//        List<MethodNameDto> methodNameList = codeBlockService.getMethodName(language, compLanguage);
//        return Result.success(methodNameList);
//    }
//
//    @GetMapping("/method_body/{method_id}")
//    public Result getMethodBody(@PathVariable(value = "method_id") Integer methodId) {
//        MethodBodyDto methodBodyDto = codeBlockService.getMethodBody(methodId);
//        return Result.success(methodBodyDto);
//    }
//
//    @PostMapping("/check")
//    public Result check(@RequestBody String code) {
//        return Result.success(codeBlockService.check(code));
//    }
//
//}
