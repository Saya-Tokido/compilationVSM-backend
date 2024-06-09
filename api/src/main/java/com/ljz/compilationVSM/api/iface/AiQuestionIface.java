package com.ljz.compilationVSM.api.iface;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.FreeQARequest;
import com.ljz.compilationVSM.api.request.OptimRequest;
import com.ljz.compilationVSM.api.request.SpecificQuestionRequest;
import com.ljz.compilationVSM.api.response.AiQAQuestionListResponse;
import com.ljz.compilationVSM.api.response.FreeQAResponse;
import com.ljz.compilationVSM.api.response.OptimResponse;
import com.ljz.compilationVSM.api.response.SpecificQuestionResponse;

/**
 * ai 问答
 */
public interface AiQuestionIface {

    /**
     * 代码优化
     *
     * @param request
     * @return
     */
    public Response<OptimResponse> optimize(OptimRequest request);

    /**
     * 自由问答
     *
     * @param request
     * @return
     */
    public Response<FreeQAResponse> askByMessage(FreeQARequest request) ;

    /**
     * 获取指定问答的问题列表
     * @return
     */
    public Response<AiQAQuestionListResponse> getQuestionList();

    /**
     * 指定问题问答
     *
     * @param request
     * @return
     */
    public Response<SpecificQuestionResponse> askByKey(SpecificQuestionRequest request);
}
