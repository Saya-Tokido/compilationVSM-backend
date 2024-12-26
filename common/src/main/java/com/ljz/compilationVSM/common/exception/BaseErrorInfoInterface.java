package com.ljz.compilationVSM.common.exception;

public interface BaseErrorInfoInterface {
    /**
     * 获取错误码
     *
     * @return 错误码
     */
    Integer getResultCode();

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    String getResultMsg();
}
