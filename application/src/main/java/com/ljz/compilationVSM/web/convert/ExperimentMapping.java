package com.ljz.compilationVSM.web.convert;

import com.ljz.compilationVSM.api.request.ExperimentRequest;
import com.ljz.compilationVSM.api.response.ExperimentResponse;
import com.ljz.compilationVSM.domain.experiment.dto.CodeDTO;
import com.ljz.compilationVSM.domain.experiment.dto.FeedbackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperimentMapping {
    CodeDTO convert(ExperimentRequest source);
    ExperimentResponse convert2(FeedbackDTO source);
}
