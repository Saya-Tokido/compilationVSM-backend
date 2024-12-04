package com.ljz.compilationVSM.dependency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationRequest2 {
    String code;
    String stdin;
    String type;
}
