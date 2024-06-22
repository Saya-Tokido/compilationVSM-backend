package com.ljz.compilationVSM.domain.experiment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodeDTO {
    String code;
    String stdin;
    String language;
    String fileext;
}
