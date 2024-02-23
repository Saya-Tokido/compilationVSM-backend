package com.ljz.compilationVSM.common.exception;


import com.ljz.compilationVSM.common.Result;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result bizExceptionHandler(HttpServletRequest req, BizException e){
        log.info("发生业务异常！原因是：{}",e.getErrorMsg());
        return Result.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, NullPointerException e){
        log.info("发生空指针异常！原因是:",e);
        return Result.error(ExceptionEnum.BODY_NOT_MATCH);
    }
    @ExceptionHandler(value = JwtException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, JwtException e){
        log.info("发生令牌校验异常！原因是:",e);
        return Result.error(ExceptionEnum.TOKEN_ERROR);
    }

    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, Exception e){
        log.info("未知异常！原因是:",e);
        return Result.error(ExceptionEnum.INTERNAL_SERVER_ERROR);
    }

}
