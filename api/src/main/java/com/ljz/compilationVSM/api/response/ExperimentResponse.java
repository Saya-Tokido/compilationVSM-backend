package com.ljz.compilationVSM.api.response;

import lombok.Data;

@Data
public class ExperimentResponse {
    String output;
    String errorMessage;
    Boolean passed;
}
