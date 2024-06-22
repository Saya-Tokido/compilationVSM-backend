package com.ljz.compilationVSM.web.soa;

import com.ljz.compilationVSM.api.base.Response;
import com.ljz.compilationVSM.api.iface.ExperimentIface;
import com.ljz.compilationVSM.api.request.ExperimentRequest;
import com.ljz.compilationVSM.api.response.ExperimentResponse;
import com.ljz.compilationVSM.domain.experiment.dto.FeedbackDTO;
import com.ljz.compilationVSM.domain.experiment.service.ExperimentService;
import com.ljz.compilationVSM.web.convert.ExperimentMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/experiment")
public class ExperimentImpl implements ExperimentIface {

    @Autowired
    private ExperimentService experimentService;
    @Autowired
    private ExperimentMapping experimentMapping;
    @PostMapping
    public Response<ExperimentResponse> checkExperiment(@RequestBody ExperimentRequest request){
        FeedbackDTO feedbackDTO = experimentService.checkExperiment(experimentMapping.convert(request));
        return Response.success(experimentMapping.convert2(feedbackDTO));
    }
}
