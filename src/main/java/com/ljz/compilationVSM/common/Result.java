package com.ljz.compilationVSM.common;


import com.ljz.compilationVSM.common.exception.BaseErrorInfoInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private Integer code;//0失败，1成功
    private String message;//响应信息
    private T data;//响应数据
    //失败响应
    //增删改成功响应
    public static Result success(){return new Result(1,"success",null);}
    public static Result success(Object data){return new Result(1,"success",data);}
    public static Result error(String message){return new Result(0,message,null);}
    public static Result error(BaseErrorInfoInterface errorInfo) {
        Result rb = new Result();
        rb.setCode(0);
        rb.setMessage(errorInfo.getResultMsg());
        rb.setData(null);
        return rb;
    }
    public static Result error(Integer code, String message) {
        Result rb = new Result();
        rb.setCode(0);
        rb.setMessage(message);
        rb.setData(null);
        return rb;
    }
}
