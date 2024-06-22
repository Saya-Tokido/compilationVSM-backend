package com.ljz.compilationVSM.dependency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompilationInputDTO {
    String code;
    String stdin;
    String language;
    String fileext;
}
