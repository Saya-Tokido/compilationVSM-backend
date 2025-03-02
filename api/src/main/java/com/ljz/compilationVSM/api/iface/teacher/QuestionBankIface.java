package com.ljz.compilationVSM.api.iface.teacher;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.teacher.ChooseDeleteRequest;
import com.ljz.compilationVSM.api.request.teacher.ChoosePageQueryRequest;
import com.ljz.compilationVSM.api.request.teacher.FillDeleteRequest;
import com.ljz.compilationVSM.api.request.teacher.FillPageQueryRequest;
import com.ljz.compilationVSM.api.response.teacher.ChoosePageQueryResponse;
import com.ljz.compilationVSM.api.response.teacher.FillPageQueryResponse;

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
}
