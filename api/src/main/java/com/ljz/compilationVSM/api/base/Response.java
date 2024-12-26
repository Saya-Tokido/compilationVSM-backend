package com.ljz.compilationVSM.api.base;


import com.ljz.compilationVSM.common.exception.BaseErrorInfoInterface;
import com.ljz.compilationVSM.common.exception.BizExceptionCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response<T> {
    private Integer code;//0成功,其余失败的错误码
    private String message;//响应信息
    private T data;//响应数据
    //失败响应
    //增删改成功响应
    public static <Void>Response<Void> success(){return new Response<>(0, "success", null);}
    public static <T>Response<T> success(T data){return new Response<>(0,"success",data);}
    public static <Void>Response<Void> error(BaseErrorInfoInterface errorInfo) {
        Response<Void> response = new Response<>();
        response.setCode(errorInfo.getResultCode());
        response.setMessage(errorInfo.getResultMsg());
        response.setData(null);
        return response;
    }

    public static <Void>Response<Void> error(BaseErrorInfoInterface errorInfo, Object... args) {
        Response<Void> response = new Response<>();
        response.setCode(errorInfo.getResultCode());
        response.setMessage(String.format(errorInfo.getResultMsg(), args));
        response.setData(null);
        return response;
    }
}
