package com.ljz.compilationVSM.domain.oj.service;

import com.ljz.compilationVSM.domain.oj.dto.*;

import java.util.List;
import java.util.Map;

/**
 * 代码评估服务
 *
 * @author ljz
 * @since 2024-12-02
 */
public interface OJService {

    /**
     * 获取函数列表
     *
     * @param requestDTO 请求DTO
     * @return 函数列表
     */
    List<MethodResponseDTO> getMethodList(MethodListRequestDTO requestDTO);

    /**
     * 获取函数体
     *
     * @param id 函数体id
     * @return 函数体
     */
    MethodBodyResponseDTO getMethodBody(Long id);

    /**
     * 校验函数代码
     *
     * @param methodId 函数名id
     * @param code     待校验代码
     * @return 校验反馈
     */
    CodeReviewResponseDTO checkMethodCode(Long methodId, String code);

    /**
     * 获取词法分析器示例输入输出
     *
     * @param language     编译的语言
     * @param compLanguage 待编译的语言
     * @return 词法分析器题目
     */
    LexerProblemResponseDTO getDemoProblem(String language, String compLanguage);

    /**
     * 校验词法分析器代码
     *
     * @param lexerId 词法分析器题目id
     * @param code    待校验代码
     * @return 校验反馈
     */
    CodeReviewResponseDTO checkLexerCode(Long lexerId, String code);

    /**
     * 获取词法分析器编程语言
     *
     * @return 词法分析对象语言与编程语言映射
     */
    Map<String, List<String>> getLexerLanguage();

    /**
     * 获取词法分析器编程语言
     *
     * @return 词法分析器编程语言
     */
    LexerLanguageResponseDTO getLexerLanguage2();

    /**
     * 获取词法分析器题目
     *
     * @param lexerId 词法分析器题id
     * @return 词法分析器题目
     */
    LexerProblemResponseDTO getLexerProblem(String lexerId);

    /**
     * 获取词法分析器作答情况
     *
     * @param requestDTO 请求参数
     * @return 词法分析器作答情况
     */
    LexerCodeReviewResponseDTO getLexerAnswer(LexerReviewRequestDTO requestDTO);

    /**
     * 获取学生最后一次提交的代码
     *
     * @return 代码数组
     */
    SourceCodeResponseDTO getLastCommitCode(String lexerId);

}
