package com.ljz.compilationVSM.web.soa.student;


import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.student.ObjQuestionIface;
import com.ljz.compilationVSM.api.request.student.*;
import com.ljz.compilationVSM.api.response.student.*;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ObjQuestionService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.ObjQuestionMapping;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/student/obj-question")
public class ObjQuestionImpl implements ObjQuestionIface {
    private final ObjQuestionMapping objQuestionMapping;
    private final ObjQuestionService objQuestionService;

    @Override
    @PostMapping("/list")
    @UserAuth(permission = PermissionEnum.OBJ_QUESTION_QUERY)
    public Response<ObjResponse> getObjQuestion(@RequestBody ObjRequest request) {
        ObjResponseDTO responseDTO = objQuestionService.getObjQuestion(objQuestionMapping.ObjQueryConvert(request));
        ObjResponse objResponse = objQuestionMapping.objResponseConvert(responseDTO);
        return Response.success(objResponse);
    }

    @Override
    @PostMapping("/check")
    @UserAuth(permission = PermissionEnum.OBJ_QUESTION_CHECK)
    public Response<ObjCheckResponse> checkObjQuestion(ObjCheckRequest request) {
        ObjCheckResponseDTO objCheckResponseDTO = objQuestionService.checkObjQuestion(objQuestionMapping.objCheckRequestDTOConvert(request));
        ObjCheckResponse response = objQuestionMapping.objCheckResponseConvert(objCheckResponseDTO);
        return Response.success(response);
    }

}
