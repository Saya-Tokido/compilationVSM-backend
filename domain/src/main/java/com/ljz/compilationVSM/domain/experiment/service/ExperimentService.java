package com.ljz.compilationVSM.domain.experiment.service;

import com.ljz.compilationVSM.domain.experiment.dto.CodeDTO;
import com.ljz.compilationVSM.domain.experiment.dto.FeedbackDTO;

public interface ExperimentService {
    public FeedbackDTO checkExperiment(CodeDTO codeDTO);
}
