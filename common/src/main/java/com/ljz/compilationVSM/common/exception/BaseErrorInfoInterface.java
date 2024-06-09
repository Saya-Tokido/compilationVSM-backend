package com.ljz.compilationVSM.common.exception;

public interface BaseErrorInfoInterface {
    /**
     *  错误码
     * @return
     */
    Integer getResultCode();

    /**
     * 错误描述
     * @return
     */
    String getResultMsg();
}
