package com.ljz.compilationVSM.common.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class BizException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    BaseErrorInfoInterface errorInfo;

    Object[] data;

    public BizException() {
        super();
    }

    public BizException(BaseErrorInfoInterface errorInfoInterface, Object... args) {
        super(errorInfoInterface.getResultMsg());
        this.errorInfo = errorInfoInterface;
        this.data = args;
    }
}
