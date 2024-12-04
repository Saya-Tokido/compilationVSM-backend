package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompLanguageEnum {

    PL0("PL0","1"),
    CANGJIE("Cangjie","2");

    private final String name;
    private final String code;

}
