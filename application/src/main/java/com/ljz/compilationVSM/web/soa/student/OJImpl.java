package com.ljz.compilationVSM.web.soa.student;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.student.OJIface;
import com.ljz.compilationVSM.api.request.common.SourceCodeResponse;
import com.ljz.compilationVSM.api.request.student.CheckCodeRequest;
import com.ljz.compilationVSM.api.request.student.CodeProblemRequest;
import com.ljz.compilationVSM.api.response.student.*;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.oj.dto.*;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.student.OJMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OJ接口实现
 *
 * @author ljz
 * @since 2024-12-01
 */
@RequestMapping("/api/student/oj")
@AllArgsConstructor
@UserAuth
@RestController
public class OJImpl implements OJIface {

    private final OJService ojService;
    private final OJMapping ojMapping;

    @PostMapping("/method-name")
    @Override
    public Response<MethodListResponse> getMethodList(@RequestBody CodeProblemRequest request) {
        List<MethodResponseDTO> methodList = ojService.getMethodList(new MethodListRequestDTO(request.getLanguage(), request.getCompLanguage()));
        return Response.success(new MethodListResponse(ojMapping.methodResponseListConvert(methodList)));
    }

    @Override
    @GetMapping("/method-body/{id}")
    public Response<MethodBodyResponse> getMethodBody(@PathVariable("id") String methodId) {
        MethodBodyResponseDTO methodBody = ojService.getMethodBody(Long.parseLong(methodId));
        return Response.success(ojMapping.methodBodyResponseConvert(methodBody));
    }

    @PostMapping("/method-body/check")
    @Override
    public Response<CodeReviewResponse> checkMethodCode(@RequestBody CheckCodeRequest request) {
        CodeReviewResponseDTO codeReviewResponseDTO = ojService.checkMethodCode(Long.parseLong(request.getProblemId()), request.getCode());
        return Response.success(new CodeReviewResponse(codeReviewResponseDTO.getStatus(), codeReviewResponseDTO.getMessage()));
    }

    @PostMapping("/lexer/demo")
    @UserAuth(permission = PermissionEnum.LEXER_DEMO_PROBLEM_QUERY)
    @Override
    public Response<LexerProblemResponse> getDemoProblem(@RequestBody CodeProblemRequest request) {
        LexerProblemResponseDTO demoProblem = ojService.getDemoProblem(request.getLanguage(), request.getCompLanguage());
        return Response.success(ojMapping.lexerProblemResponseConvert(demoProblem));
    }

    @PostMapping("/lexer/check")
    @Override
    @UserAuth(permission = PermissionEnum.LEXER_CODE_CHECK)
    public Response<CodeReviewResponse> checkLexerCode(@RequestBody CheckCodeRequest request) {
        CodeReviewResponseDTO codeReviewResponseDTO = ojService.checkLexerCode(Long.parseLong(request.getProblemId()), request.getCode());
        return Response.success(new CodeReviewResponse(codeReviewResponseDTO.getStatus(), codeReviewResponseDTO.getMessage()));
    }

    @Override
    @GetMapping("/lexer/language")
    @UserAuth(permission = PermissionEnum.LEXER_LANGUAGE_QUERY)
    public Response<LexerLanguageResponse> getLexerLanguage() {
        Map<String, List<String>> lexerLanguage = ojService.getLexerLanguage();
        List<LexerLanguageResponse.LanguageMap> languageMapList = lexerLanguage.entrySet().stream()
                .map(entry -> new LexerLanguageResponse.LanguageMap(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return Response.success(new LexerLanguageResponse(languageMapList));
    }

    @Override
    @GetMapping("/lexer/last-commit/{lexer-id}")
    @UserAuth(permission = PermissionEnum.LEXER_LAST_COMMIT_CODE_QUERY)
    public Response<SourceCodeResponse> getLastCommitCode(@PathVariable(value = "lexer-id")String lexerId) {
        SourceCodeResponseDTO lastCommitCode = ojService.getLastCommitCode(lexerId);
        SourceCodeResponse response = ojMapping.sourceCodeResponseConvert(lastCommitCode);
        return Response.success(response);
    }
}
