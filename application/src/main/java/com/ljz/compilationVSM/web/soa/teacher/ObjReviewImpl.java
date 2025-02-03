package com.ljz.compilationVSM.web.soa.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.ObjReviewIface;
import com.ljz.compilationVSM.api.request.teacher.ObjScoreModifyRequest;
import com.ljz.compilationVSM.api.response.teacher.ObjAnswerInfoResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ObjAnswerInfoResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ObjQuestionService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.teacher.ObjReviewMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 学生客观题答题情况评阅接口实现
 *
 * @author ljz
 * @since 2025-01-30
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/teacher/obj-review")
public class ObjReviewImpl implements ObjReviewIface {

    private final ObjQuestionService objQuestionService;
    private final ObjReviewMapping objReviewMapping;

    @Override
    @GetMapping("/info/{number}")
    @UserAuth(permission = PermissionEnum.OBJ_REVIEW)
    public Response<ObjAnswerInfoResponse> getObjAnswerInfo(@PathVariable(value = "number") String number) {
        ObjAnswerInfoResponseDTO responseDTO = objQuestionService.getObjAnswerInfo(number);
        return Response.success(objReviewMapping.convert(responseDTO));
    }

    @Override
    @PostMapping("/score-modify")
    @UserAuth(permission = PermissionEnum.OBJ_SCORE_MODIFY)
    public Response<Void> modifyObjGrade(@RequestBody ObjScoreModifyRequest request) {
        objQuestionService.modifyObjScore(objReviewMapping.convert(request));
        return Response.success();
    }
}
