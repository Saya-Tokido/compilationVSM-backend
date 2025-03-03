package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.teacher.*;
import com.ljz.compilationVSM.api.response.teacher.ChoosePageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.FillPageQueryResponse;
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
}
