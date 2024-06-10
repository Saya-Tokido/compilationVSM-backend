//package com.ljz.compilationVSM.web.soa;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//
//@Slf4j
//@RestController
//
//@RequestMapping("/question")
//public class QuestionImpl {
//    @Autowired
//    private ChooseService chooseService;
//    @Autowired
//    private FillService fillService;
//    @GetMapping("/{questionType}")
//    public Result<Object> getQuestion(
//    @PathVariable("questionType") String type,
//    @RequestParam(value = "number", required = false) int number){
//        if(type.equals("choose")){
//            List<ChooseDto> body=chooseService.getQuestion(number);
//            return Result.success(body);
//        }
//        else{
//            List<FillDto> body=fillService.getQuestion(number);
//            return Result.success(body);
//        }
//    }
//    @PostMapping("/{questionType}")
//    public Result<Object> checkAnswer(
//            @PathVariable("questionType") String type,
//            @RequestBody List<CheckUnit> checkBody){
//        if(type.equals("choose")){
//            chooseService.checkAnswer(checkBody);
//            return Result.success(checkBody);
//        }
//        else{
//            fillService.checkAnswer(checkBody);
//            return Result.success(checkBody);
//        }
//    }
//}
