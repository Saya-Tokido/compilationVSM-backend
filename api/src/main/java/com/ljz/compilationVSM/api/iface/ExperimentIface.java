package com.ljz.compilationVSM.api.iface;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.request.ExperimentRequest;
import com.ljz.compilationVSM.api.response.ExperimentResponse;

public interface ExperimentIface {

    public Response<ExperimentResponse> checkExperiment(ExperimentRequest request);
}
