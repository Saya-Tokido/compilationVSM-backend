package com.ljz.compilationVSM.web.soa.student;


import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.student.AiQuestionIface;
import com.ljz.compilationVSM.api.request.student.FreeQARequest;
import com.ljz.compilationVSM.api.request.student.OptimRequest;
import com.ljz.compilationVSM.api.request.student.SpecificQuestionRequest;
import com.ljz.compilationVSM.api.response.student.AiQAQuestionListResponse;
import com.ljz.compilationVSM.api.response.student.FreeQAResponse;
import com.ljz.compilationVSM.api.response.student.OptimResponse;
import com.ljz.compilationVSM.api.response.student.SpecificQuestionResponse;
import com.ljz.compilationVSM.domain.aiQA.dto.FreeQAAnswerDTO;
import com.ljz.compilationVSM.domain.aiQA.dto.SpecificAnswerDTO;
import com.ljz.compilationVSM.web.convert.student.AiQAMapping;
import com.ljz.compilationVSM.domain.aiQA.dto.OptimizedDTO;
import com.ljz.compilationVSM.domain.aiQA.service.AiQAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ai 问答接口实现
 * @author ljz
 * @since 2024-12-01
 */
@RestController
@Slf4j
@RequestMapping("/aiqa")
public class AiQuestionImpl implements AiQuestionIface {

    @Autowired
    private AiQAService aiQAService;
    @Autowired
    private AiQAMapping aiQAMapping;

    /**
     * 代码优化
     *
     * @param request
     * @return
     */
    @PostMapping("/optim")
    public Response<OptimResponse> optimize(@RequestBody OptimRequest request) {
        OptimizedDTO optimize = aiQAService.optimize(aiQAMapping.optimConvert(request));
        return Response.success(aiQAMapping.optimConvert2(optimize));
    }

    /**
     * 自由问答
     *
     * @param request
     * @return
     */
    @PostMapping("/knowledge/free")
    public Response<FreeQAResponse> askByMessage(@RequestBody FreeQARequest request) {
        FreeQAAnswerDTO freeQAAnswerDTO = aiQAService.askByMessage(aiQAMapping.freeQAConvert(request));
        return Response.success(aiQAMapping.freeQAConvert2(freeQAAnswerDTO));
    }

    /**
     * 获取指定问答的问题列表
     * @return
     */
    @GetMapping("/knowledge/questionlist")
    public Response<AiQAQuestionListResponse> getQuestionList(){
        AiQAQuestionListResponse response = new AiQAQuestionListResponse(aiQAService.getQuestionList());
        return Response.success(response);
    }

    /**
     * 指定问题问答
     *
     * @param request
     * @return
     */
    @PostMapping("/knowledge/specific_question")
    public Response<SpecificQuestionResponse> askByKey(@RequestBody SpecificQuestionRequest request) {
        SpecificAnswerDTO specificAnswerDTO = aiQAService.askByKey(aiQAMapping.specificConvert(request));
        return Response.success(aiQAMapping.specificConvert2(specificAnswerDTO));
    }
}
