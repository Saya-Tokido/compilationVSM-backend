package com.ljz.compilationVSM.web.soa.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.OJReviewIface;
import com.ljz.compilationVSM.api.request.teacher.LexerReviewRequest;
import com.ljz.compilationVSM.api.response.common.LexerLanguageResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerCodeReviewResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerDemoProblemResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.oj.dto.LexerCodeReviewResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.LexerLanguageResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.LexerProblemResponseDTO;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.teacher.OJReviewMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 编程题教师评估接口实现
 *
 * @author ljz
 * @since 2025-02-04
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/teacher/oj-review")
public class OJReviewImpl implements OJReviewIface {

    private final OJService ojService;
    private final OJReviewMapping ojReviewMapping;

    @Override
    @GetMapping("/language")
    @UserAuth(permission = PermissionEnum.LEXER_LANGUAGE_QUERY_REVIEW)
    public Response<LexerLanguageResponse> getLexerLanguage() {
        LexerLanguageResponseDTO responseDTO = ojService.getLexerLanguage2();
        return Response.success(ojReviewMapping.convert(responseDTO));
    }

    @Override
    @GetMapping("/problem/{lexer-id}")
    @UserAuth(permission = PermissionEnum.LEXER_DEMO_PROBLEM_QUERY_REVIEW)
    public Response<LexerDemoProblemResponse> getLexerProblem(@PathVariable(value = "lexer-id") String lexerId) {
        LexerProblemResponseDTO responseDTO = ojService.getLexerProblem(lexerId);
        return Response.success(ojReviewMapping.convert(responseDTO));
    }

    @Override
    @PostMapping("/answer")
    @UserAuth(permission = PermissionEnum.LEXER_CODE_REVIEW)
    public Response<LexerCodeReviewResponse> getLexerAnswer(@RequestBody LexerReviewRequest request) {
        LexerCodeReviewResponseDTO responseDTO = ojService.getLexerAnswer(ojReviewMapping.convert(request));
        return Response.success(ojReviewMapping.convert(responseDTO));
    }
}
