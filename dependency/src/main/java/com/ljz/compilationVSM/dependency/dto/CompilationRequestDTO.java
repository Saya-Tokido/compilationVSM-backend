package com.ljz.compilationVSM.dependency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationRequestDTO {
    String code;
    String stdin;
    String language;
    String fileext;
    String token;
}
