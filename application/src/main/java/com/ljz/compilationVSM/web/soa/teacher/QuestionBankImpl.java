package com.ljz.compilationVSM.web.soa.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.QuestionBankIface;
import com.ljz.compilationVSM.api.request.teacher.*;
import com.ljz.compilationVSM.api.response.teacher.ChoosePageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.FillPageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerDetailResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerPageQueryResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.ExcelUtil;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.*;
import com.ljz.compilationVSM.domain.ObjQuestion.service.ObjQuestionService;
import com.ljz.compilationVSM.domain.oj.dto.LexerPageQueryResponseDTO;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.api.valid.Valid;
import com.ljz.compilationVSM.web.convert.teacher.QuestionBankMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
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
    private final OJService ojService;
    private final ExcelUtil excelUtil;

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

    @Override
    @PostMapping("/choose-add")
    @UserAuth(permission = PermissionEnum.CHOOSE_ADD)
    public Response<Void> addChoose(@RequestBody @Valid ChooseAddRequest request) {
        objQuestionService.addChoose(questionBankMapping.convert(request));
        return Response.success();
    }

    @Override
    @PostMapping("/fill-add")
    @UserAuth(permission = PermissionEnum.FILL_ADD)
    public Response<Void> addFill(@RequestBody @Valid FillAddRequest request) {
        objQuestionService.addFill(questionBankMapping.convert(request));
        return Response.success();
    }

    @Override
    @PostMapping("/choose-import")
    @UserAuth(permission = PermissionEnum.CHOOSE_BATCH_ADD)
    public Response<Void> addChooseByExcel(@RequestBody MultipartFile file) {
        try {
            List<ChooseAddRequestDTO> chooseAddDTOList = excelUtil.importFromExcel(file.getBytes(), ChooseAddRequestDTO.class);
            objQuestionService.addChooseBatch(chooseAddDTOList);
            return Response.success();
        } catch (Exception ex) {
            if (ex instanceof BizException bizException) {
                throw bizException;
            } else {
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                log.error("教师通过Excel批量添加选择题, 触发已检查异常, 异常信息\n {}", sw);
                throw new BizException(BizExceptionCodeEnum.EXCEL_FORMAT_ERROR);
            }
        }
    }

    @Override
    @PostMapping("/fill-import")
    @UserAuth(permission = PermissionEnum.FILL_BATCH_ADD)
    public Response<Void> addFillByExcel(@RequestBody MultipartFile file) {
        try {
            List<FillAddRequestDTO> fillAddDTOList = excelUtil.importFromExcel(file.getBytes(), FillAddRequestDTO.class);
            objQuestionService.addFillBatch(fillAddDTOList);
            return Response.success();
        } catch (Exception ex) {
            if (ex instanceof BizException bizException) {
                throw bizException;
            } else {
                StringWriter sw = new StringWriter();
                ex.printStackTrace(new PrintWriter(sw));
                log.error("教师通过Excel批量添加填空题, 触发已检查异常, 异常信息\n {}", sw);
                throw new BizException(BizExceptionCodeEnum.EXCEL_FORMAT_ERROR);
            }
        }
    }

    @Override
    @PostMapping("/lexer-page")
    @UserAuth(permission = PermissionEnum.PAGE_LEXER_BANK)
    public Response<LexerPageQueryResponse> pageQueryLexer(@RequestBody LexerPageQueryRequest request) {
        LexerPageQueryResponseDTO responseDTO = ojService.pageQueryLexer(questionBankMapping.convert(request));
        LexerPageQueryResponse response = questionBankMapping.convert(responseDTO);
        return Response.success(response);
    }

    @Override
    @PostMapping("/lexer-save")
    @UserAuth(permission = PermissionEnum.LEXER_BANK_SAVE)
    public Response<Long> saveLexer(@RequestBody @Valid LexerSaveRequest request) {
        Long response = ojService.saveLexer(questionBankMapping.convert(request));
        return Response.success(response);
    }

    @Override
    public Response<LexerDetailResponse> getLexerDetail(Long id) {

        return null;
    }
}
