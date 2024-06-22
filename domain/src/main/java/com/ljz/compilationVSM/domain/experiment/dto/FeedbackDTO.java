package com.ljz.compilationVSM.domain.experiment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeedbackDTO {
    String output;
    String errorMessage;
    Boolean passed;
}
