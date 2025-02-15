package com.ljz.compilationVSM.web.soa.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.teacher.OJReviewIface;
import com.ljz.compilationVSM.api.request.teacher.LexerCodePDRequest;
import com.ljz.compilationVSM.api.request.teacher.LexerPlaStudentExportRequest;
import com.ljz.compilationVSM.api.request.teacher.LexerReviewRequest;
import com.ljz.compilationVSM.api.response.common.LexerLanguageResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerCodeReviewResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerDemoProblemResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerPDInfoResponse;
import com.ljz.compilationVSM.common.enums.PermissionEnum;
import com.ljz.compilationVSM.common.exception.BizException;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import com.ljz.compilationVSM.common.utils.ExcelUtil;
import com.ljz.compilationVSM.domain.ObjQuestion.dto.StudentBaseInfoResponseDTO;
import com.ljz.compilationVSM.domain.oj.dto.*;
import com.ljz.compilationVSM.domain.oj.service.OJService;
import com.ljz.compilationVSM.web.config.aspect.UserAuth;
import com.ljz.compilationVSM.web.convert.teacher.OJReviewMapping;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private final ExcelUtil excelUtil;

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

    @Override
    @GetMapping("/pd/info")
    @UserAuth(permission = PermissionEnum.LEXER_CODE_PD_INFO_QUERY)
    public Response<LexerPDInfoResponse> getPDInfo() {
        LexerPDInfoResponseDTO responseDTO = ojService.getPdInfo();
        return Response.success(ojReviewMapping.convert(responseDTO));
    }

    @Override
    @PostMapping("/pd/execute")
    @UserAuth(permission = PermissionEnum.LEXER_CODE_PD_EXECUTE)
    public Response<Integer> lexerCodePD(@RequestBody LexerCodePDRequest request) {
        Integer response = ojService.lexerCodePD(request.getTeachClass());
        return Response.success(response);
    }

    @Override
    @PostMapping("/pd/export")
    @UserAuth(permission = PermissionEnum.LEXER_PD_STUDENT_EXPORT)
    public void exportPlaStudent(@RequestBody LexerPlaStudentExportRequest request, HttpServletResponse response) {
        LexerPlaStudentInfoResponseDTO responseDTO = ojService.getPlaStudentInfo(request.getTeachClass());
        String sheetName = "plagiarism-student";
        try{
            byte[] bytes = excelUtil.exportToExcel(responseDTO.getStudentList(), sheetName, StudentBaseInfoResponseDTO.class);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String encodedFileName = URLEncoder.encode(sheetName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=\""+encodedFileName+".xlsx\"");
            // 输出Excel文件到响应流
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        }catch (IOException ioException){
            log.error("词法分析器题代码抄袭学生信息导出失败,teach class = {}",request.getTeachClass());
            throw new BizException(BizExceptionCodeEnum.SERVER_ERROR);
        }
    }
}
