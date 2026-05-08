package com.bwie.handler;



import com.bwie.utils.R;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {


    /**
     * 捕获 @Validated 参数校验异常（最常用）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleBindException(MethodArgumentNotValidException e) {
        // 获取第一个错误信息
        FieldError fieldError = e.getBindingResult().getFieldError();
        String msg = fieldError != null ? fieldError.getDefaultMessage() : "参数校验失败";

        return R.error().message(msg);
    }

    /**
     * 处理MyRuntimeException异常
     * @param e
     * @return
     */
    @ExceptionHandler(MyRuntimeException.class)
    public R errorExceptionHandler(Exception e){
       e.printStackTrace();

        return R.error().message(e.getMessage());
    }

}
