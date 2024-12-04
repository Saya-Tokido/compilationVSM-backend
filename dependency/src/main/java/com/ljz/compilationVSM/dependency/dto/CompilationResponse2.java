package com.ljz.compilationVSM.dependency.dto;

import lombok.Data;


@Data
public class CompilationResponse2 {

    /**
     * 接口调用状态 0 为成功，1为失败
     */
    private Integer code;

    /**
     * 编译执行结果
     */
    private ResponseData data;
    @Data
    public static class ResponseData{
        /**
         * 输出
         */
        private String output;

        /**
         * 编译执行情况 0 为成功，1为失败
         */
        private Integer code;

        /**
         * 执行时间
         */
        private Integer time;

        /**
         * 错误信息
         */
        private String message;
    }
}
