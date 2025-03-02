package com.ljz.compilationVSM.web.soa.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.QuestionBankIface;
import com.ljz.compilationVSM.api.request.teacher.ChooseDeleteRequest;
import com.ljz.compilationVSM.api.request.teacher.ChoosePageQueryRequest;
import com.ljz.compilationVSM.api.request.teacher.FillDeleteRequest;
import com.ljz.compilationVSM.api.request.teacher.FillPageQueryRequest;
import com.ljz.compilationVSM.api.response.teacher.ChoosePageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.FillPageQueryResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChoosePageQueryRequestDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.ChoosePageQueryResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillPageQueryRequestDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.FillPageQueryResponseDTO;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ObjQuestionService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.teacher.QuestionBankMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 题库接口实现
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/teacher/question-bank")
public class QuestionBankImpl implements QuestionBankIface {

    private final QuestionBankMapping questionBankMapping;
    private final ObjQuestionService objQuestionService;

    @Override
    @PostMapping("/choose-page")
    @UserAuth(permission = PermissionEnum.PAGE_CHOOSE_BANK)
    public Response<ChoosePageQueryResponse> pageQueryChoose(@RequestBody ChoosePageQueryRequest request) {
        ChoosePageQueryRequestDTO requestDTO = questionBankMapping.convert(request);
        requestDTO.setTitle(Objects.isNull(requestDTO.getTitle()) ? "" : requestDTO.getTitle());
        ChoosePageQueryResponseDTO responseDTO = objQuestionService.pageChooseBank(requestDTO);
        ChoosePageQueryResponse response = questionBankMapping.convert(responseDTO);
        return Response.success(response);
    }

    @Override
    @PostMapping("/fill-page")
    @UserAuth(permission = PermissionEnum.PAGE_FILL_BANK)
    public Response<FillPageQueryResponse> pageQueryFill(@RequestBody FillPageQueryRequest request) {
        FillPageQueryRequestDTO requestDTO = questionBankMapping.convert(request);
        requestDTO.setTitle(Objects.isNull(requestDTO.getTitle()) ? "" : requestDTO.getTitle());
        FillPageQueryResponseDTO responseDTO = objQuestionService.pageFillBank(requestDTO);
        FillPageQueryResponse response = questionBankMapping.convert(responseDTO);
        return Response.success(response);
    }

    @Override
    @PostMapping("/choose-delete")
    @UserAuth(permission = PermissionEnum.CHOOSE_DELETE)
    public Response<Void> deleteChoose(@RequestBody ChooseDeleteRequest request) {
        objQuestionService.deleteChoose(request.getId());
        return Response.success();
    }

    @Override
    @PostMapping("/fill-delete")
    @UserAuth(permission = PermissionEnum.FILL_DELETE)
    public Response<Void> deleteFill(@RequestBody FillDeleteRequest request) {
        objQuestionService.deleteFill(request.getId());
        return Response.success();
    }
}
