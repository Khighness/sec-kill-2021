package top.parak.exception;

import org.apache.ibatis.javassist.compiler.ast.BinExpr;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.parak.response.CodeMessage;
import top.parak.response.ServerResponse;

import javax.servlet.ServletResponse;
import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理普通异常
     */
    @ExceptionHandler(value = Exception.class)
    public ServerResponse<String> handleException(Exception e) {
        e.printStackTrace();
        return ServerResponse.error(e.getClass().getSimpleName() + ":" + e.getMessage());
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(value = GlobalException.class)
    public ServerResponse<String> handleGlobalException(GlobalException e) {
        return new ServerResponse<>(e.getCodeMessage());
    }

    /**
     * 处理参数异常
     */
    @ExceptionHandler(value = BindException.class)
    public ServerResponse<String> handleBindException(BindException e) {
        List<ObjectError> errors = e.getAllErrors();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < errors.size() - 1; i++) {
            builder.append(errors.get(i).getDefaultMessage()).append(';');
        }
        builder.append(errors.get(errors.size() - 1).getDefaultMessage()).append(".");
        return new ServerResponse<>(CodeMessage.PARAM_VALIDATE_ERROR, builder.toString());
    }
}
