package com.ljz.compilationVSM.api.request;

import lombok.Data;

@Data
public class ExperimentRequest {
    String code;
    String stdin;
    String language;
    String fileext;
}
