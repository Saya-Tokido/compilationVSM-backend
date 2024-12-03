package com.ljz.compilationVSM.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompileStatusEnum {

    SUCCESS(0,"通过全部用例"),
    COMPILE_ERROR(1,"编译出错"),
    PARTIAL_ERROR(2,"未能通过全部用例");

    private final Integer code;

    private final String desc;
}
