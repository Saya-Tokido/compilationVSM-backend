package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.teacher.LexerCodePDRequest;
import com.ljz.compilationVSM.api.request.teacher.LexerPlaStudentExportRequest;
import com.ljz.compilationVSM.api.request.teacher.LexerReviewRequest;
import com.ljz.compilationVSM.api.response.common.LexerLanguageResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerCodeReviewResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerDemoProblemResponse;
import com.ljz.compilationVSM.api.response.teacher.LexerPDInfoResponse;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 教师评估编程题接口
 *
 * @author ljz
 * @since 2025-02-04
 */
public interface OJReviewIface {

    /**
     * 获取词法分析器题编程语言
     *
     * @return 成功
     */
    Response<LexerLanguageResponse> getLexerLanguage();

    /**
     * 获取词法分析器题目内容
     *
     * @param lexerId 词法分析器题id
     * @return 词法分析器题目内容
     */
    Response<LexerDemoProblemResponse> getLexerProblem(String lexerId);

    /**
     * 获取词法分析器题学生作答情况
     *
     * @param request 请求参数
     * @return 词法分析器题学生作答情况
     */
    Response<LexerCodeReviewResponse> getLexerAnswer(LexerReviewRequest request);

    /**
     * 代码查重信息获取
     *
     * @return 代码查重信息
     */
    Response<LexerPDInfoResponse> getPDInfo();

    /**
     * 词法分析器题代码查重
     *
     * @param request 请求参数
     * @return 查重映射对数
     */
    Response<Integer> lexerCodePD(LexerCodePDRequest request);

    /**
     * 导出抄袭的学生信息为xlsx文件
     *
     * @param request  请求参数
     * @param response 响应处理器
     */
    void exportPlaStudent(LexerPlaStudentExportRequest request, HttpServletResponse response);
}
