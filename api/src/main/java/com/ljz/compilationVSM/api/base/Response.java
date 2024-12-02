package com.ljz.compilationVSM.api.base;


import com.ljz.compilationVSM.common.exception.BaseErrorInfoInterface;
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
    public static <Void>Response<Void> success(){return new Response(0,"success",null);}
    public static <T>Response<T> success(T data){return new Response(0,"success",data);}
    public static <Void>Response<Void> error(String message){return new Response(500,message,null);}
    public static <Void>Response<Void> error(BaseErrorInfoInterface errorInfo) {
        Response rb = new Response();
        rb.setCode(500);
        rb.setMessage(errorInfo.getResultMsg());
        rb.setData(null);
        return rb;
    }
    public static <Void>Response<Void> error(Integer code, String message) {
        Response rb = new Response();
        rb.setCode(500);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }
}
