package com.ljz.compilationVSM.web.soa;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.OJIface;
import com.ljz.compilationVSM.api.request.CheckCodeRequest;
import com.ljz.compilationVSM.api.request.CodeProblemRequest;
import com.ljz.compilationVSM.api.response.CodeReviewResponse;
import com.ljz.compilationVSM.api.response.LexerProblemResponse;
import com.ljz.compilationVSM.api.response.MethodBodyResponse;
import com.ljz.compilationVSM.api.response.MethodListResponse;
import com.ljz.compilationVSM.domain.oj.dto.*;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.web.convert.OJMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OJ接口实现
 * @author ljz
 * @since 2024-12-01
 */
@RequestMapping("/master/question")
@AllArgsConstructor
@RestController
public class OJImpl implements OJIface {

    private final OJService ojService;
    private final OJMapping ojMapping;

    @PostMapping("/method_name")
    @Override
    public Response<MethodListResponse> getMethodList(@RequestBody CodeProblemRequest request) {
        List<MethodResponseDTO> methodList = ojService.getMethodList(new MethodListRequestDTO(request.getLanguage(), request.getCompLanguage()));
        return Response.success(new MethodListResponse(ojMapping.methodResponseListConvert(methodList)));
    }

    @Override
    @GetMapping("/method_body/{id}")
    public Response<MethodBodyResponse> getMethodBody(@PathVariable("id") String methodId) {
        MethodBodyResponseDTO methodBody = ojService.getMethodBody(Long.parseLong(methodId));
        return Response.success(ojMapping.methodBodyResponseConvert(methodBody));
    }

    @PostMapping("/method_body/check")
    @Override
    public Response<CodeReviewResponse> checkMethodCode(@RequestBody CheckCodeRequest request) {
        CodeReviewResponseDTO codeReviewResponseDTO = ojService.checkMethodCode(Long.parseLong(request.getProblemId()), request.getCode());
        return Response.success(new CodeReviewResponse(codeReviewResponseDTO.getStatus(),codeReviewResponseDTO.getMessage()));
    }

    @PostMapping("/lexer")
    @Override
    public Response<LexerProblemResponse> getDemoProblem(@RequestBody CodeProblemRequest request) {
        LexerProblemResponseDTO demoProblem = ojService.getDemoProblem(request.getLanguage(), request.getCompLanguage());
        return Response.success(ojMapping.lexerProblemResponseConvert(demoProblem));
    }

    @PostMapping("/lexer/check")
    @Override
    public Response<CodeReviewResponse> checkLexerCode(@RequestBody CheckCodeRequest request) {
        CodeReviewResponseDTO codeReviewResponseDTO = ojService.checkLexerCode(Long.parseLong(request.getProblemId()), request.getCode());
        return Response.success(new CodeReviewResponse(codeReviewResponseDTO.getStatus(),codeReviewResponseDTO.getMessage()));
    }
}
