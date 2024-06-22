package com.ljz.compilationVSM.api.iface;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.ChooseCheckRequest;
import com.ljz.compilationVSM.api.request.ChooseRequest;
import com.ljz.compilationVSM.api.request.FillCheckRequest;
import com.ljz.compilationVSM.api.request.FillRequest;
import com.ljz.compilationVSM.api.response.ChooseCheckResponse;
import com.ljz.compilationVSM.api.response.ChooseListResponse;
import com.ljz.compilationVSM.api.response.FillCheckResponse;
import com.ljz.compilationVSM.api.response.FillListResponse;

/**
 * 客观题接口
 */
public interface ObjQuestionIface {
    /**
     * 获取选择题
     * @param request
     * @return
     */
    public Response<ChooseListResponse> getChoose(ChooseRequest request);

    /**
     * 获取填空题
     * @param request
     * @return
     */
    public Response<FillListResponse> getFill(FillRequest request);

    /**
     * 校验选择题
     * @param request
     * @return
     */
    public Response<ChooseCheckResponse> checkChoose(ChooseCheckRequest request);

    /**
     * 校验填空题
     * @param request
     * @return
     */
    public Response<FillCheckResponse> checkFill(FillCheckRequest request);


}
