package com.ljz.compilationVSM.api.iface;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.ChooseCheckRequest;
import com.ljz.compilationVSM.api.request.ChooseRequest;
import com.ljz.compilationVSM.api.request.FillCheckRequest;
import com.ljz.compilationVSM.api.request.FillRequest;
import com.ljz.compilationVSM.api.response.*;

/**
 * 客观题接口
 *
 * @author ljz
 * @since 2024-12-05
 */
public interface ObjQuestionIface {
    /**
     * 获取选择题
     * @param request
     * @return
     */
    Response<ChooseListResponse> getChoose(ChooseRequest request);

    /**
     * 获取填空题
     * @param request
     * @return
     */
    Response<FillListResponse> getFill(FillRequest request);

    /**
     * 校验选择题
     * @param request
     * @return
     */
    Response<ChooseCheckResponse> checkChoose(ChooseCheckRequest request);

    /**
     * 校验填空题
     * @param request
     * @return
     */
    Response<FillCheckResponse> checkFill(FillCheckRequest request);

}
