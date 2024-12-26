package com.ljz.compilationVSM.api.iface.student;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.student.CheckCodeRequest;
import com.ljz.compilationVSM.api.request.student.CodeProblemRequest;
import com.ljz.compilationVSM.api.response.student.*;

/**
 * 代码评估接口
 * @author ljz
 * @since 2024-12-02
 */
public interface OJIface {

    /**
     * 获取函数名列表
     * @param request 获取函数名列表请求
     * @return  函数名列表响应
     */
    Response<MethodListResponse> getMethodList(CodeProblemRequest request);

    /**
     * 获取函数体
     * @param methodId 函数名id
     * @return 函数体响应
     */
    Response<MethodBodyResponse> getMethodBody(String methodId);

    /**
     * 校验函数代码
     * @param request 代码校验请求
     * @return 代码评估结果
     */
    Response<CodeReviewResponse> checkMethodCode(CheckCodeRequest request);

    /**
     * 获取词法分析器示例输入输出
     * @param request 代码题获取请求
     * @return 词法分析器题目
     */
    Response<LexerProblemResponse> getDemoProblem(CodeProblemRequest request);

    /**
     * 校验词法分析器代码
     * @param request 代码校验请求
     * @return 代码评估结果
     */
    Response<CodeReviewResponse> checkLexerCode(CheckCodeRequest request);

    /**
     * 获取词法分析器编程语言
     *
     * @return 词法分析器编程语言
     */
    Response<LexerLanguageResponse> getLexerLanguage();
}
