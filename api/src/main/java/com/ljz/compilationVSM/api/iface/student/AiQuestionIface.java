package com.ljz.compilationVSM.api.iface.student;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.student.FreeQARequest;
import com.ljz.compilationVSM.api.request.student.OptimRequest;
import com.ljz.compilationVSM.api.request.student.SpecificQuestionRequest;
import com.ljz.compilationVSM.api.response.student.AiQAQuestionListResponse;
import com.ljz.compilationVSM.api.response.student.FreeQAResponse;
import com.ljz.compilationVSM.api.response.student.OptimResponse;
import com.ljz.compilationVSM.api.response.student.SpecificQuestionResponse;
import reactor.core.publisher.Flux;

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
    Flux<String> optimize(OptimRequest request);

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
