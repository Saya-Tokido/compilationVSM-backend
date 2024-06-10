package com.ljz.compilationVSM.api.iface;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.ChooseRequest;
import com.ljz.compilationVSM.api.request.FillRequest;
import com.ljz.compilationVSM.api.response.ChooseListResponse;
import com.ljz.compilationVSM.api.response.FillListResponse;

public interface ObjQuestionIface {
    public Response<ChooseListResponse> getChoose(ChooseRequest request);
    public Response<FillListResponse> getFill(FillRequest request);
}
