package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.teacher.*;
import com.ljz.compilationVSM.api.response.teacher.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 题库接口
 *
 * @author 劳金赞
 * @since 2025-03-02
 */
public interface QuestionBankIface {

    /**
     * 分页查询选择题题库
     *
     * @param request 请求
     * @return 选择题题库分页
     */
    Response<ChoosePageQueryResponse> pageQueryChoose(ChoosePageQueryRequest request);

    /**
     * 分页查询填空题题库
     *
     * @param request 请求
     * @return 填空题题库分页
     */
    Response<FillPageQueryResponse> pageQueryFill(FillPageQueryRequest request);

    /**
     * 选择题删除请求
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> deleteChoose(ChooseDeleteRequest request);

    /**
     * 填空题删除请求
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> deleteFill(FillDeleteRequest request);

    /**
     * 新增选择题
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> addChoose(ChooseAddRequest request);

    /**
     * 新增填空题
     *
     * @param request 请求
     * @return 成功
     */
    Response<Void> addFill(FillAddRequest request);

    /**
     * 通过excel批量导入选择题
     *
     * @param file excel文件
     * @return 成功
     */
    Response<Void> addChooseByExcel(MultipartFile file);

    /**
     * 通过excel批量添加填空题
     *
     * @param file excel文件
     * @return 成功
     */
    Response<Void> addFillByExcel(MultipartFile file);

    /**
     * 分页查询词法分析器题库
     *
     * @param request 请求参数
     * @return 词法分析器题库分页
     */
    Response<LexerPageQueryResponse> pageQueryLexer(LexerPageQueryRequest request);

    /**
     * 保存词法分析器题
     *
     * @param request 请求
     * @return 词法分析器id
     */
    Response<Long> saveLexer(LexerSaveRequest request);

    /**
     * 获取词法分析器题详情
     *
     * @param id 词法分析器题id
     * @return 词法分析器题详情
     */
    Response<LexerDetailResponse> getLexerDetail(Long id);

    /**
     * 分页查询词法分析器题用例
     *
     * @param request 请求
     * @return 词法分析题用例分页
     */
    Response<LexerTestcasePageResponse> pageQueryLexerTestcase(LexerTestcasePageRequest request);

    /**
     * 添加词法分析器题用例
     *
     * @param request 请求
     * @return 添加成功
     */
    Response<Void> addLexerTestcase(LexerTestcaseAddRequest request);

    /**
     * 删除词法分析器题用例
     *
     * @param request 请求
     * @return 删除成功
     */
    Response<Void> deleteLexerTestcase(LexerTestcaseDeleteRequest request);
}
