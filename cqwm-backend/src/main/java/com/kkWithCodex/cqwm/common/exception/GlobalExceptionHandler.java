package com.kkWithCodex.cqwm.common.exception;

import com.kkWithCodex.cqwm.common.result.ApiResponse;
import com.kkWithCodex.cqwm.common.result.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Intercepts exceptions and converts them into unified API payloads.、
 * 拦截所有 Controller 抛出的异常，并把异常转换成统一的 ApiResponse 返回给前端。
 * 让你接口里不用 try-catch，只要 throw 异常，就自动变成统一格式返回。
 */
@RestControllerAdvice
//这是 全局异常捕获器。只要 Controller 里抛了异常，就会被这里的代码接住。
public class GlobalExceptionHandler {

    //这句代码做的事情是：
    //用 LoggerFactory 创建一个专属于当前类（GlobalExceptionHandler）的日志记录器（Logger）。
    //传入的 GlobalExceptionHandler.class 是 Class 对象，不是“字节码文件”，而是 Java 用来表示“哪个类”的一种结构。
    //Log 工厂会根据这个 Class 名字，自动给你的日志加上类名标签，比如：
    //2024-01-01 12:00:00 INFO  c.k.c.common.exception.GlobalExceptionHandler
    //这样日志就知道“是哪个类打印的”。
    //工厂模式（Factory Pattern）核心思想：
    //把创建对象的逻辑封装起来，让用户只需要调用工厂方法即可得到对象，不需要知道对象是如何构造的。
    //例如 Logger 是接口，真正创建的可能是 LogbackLogger、Log4jLogger 等不同实现。
    //你并不关心，它帮你自动选。
    //为什么传入 GlobalExceptionHandler.class？日志前缀就会是：
    //GlobalExceptionHandler这样不同类写的日志可以区分来源。
    //同时传入class对象，你每个类用自己的 logger，不会重复 new。 会直接查缓存map来找到已有的logger对象
    //是告诉日志系统“这个日志属于哪个类”，方便日志归类与配置。LoggerFactory 是工厂模式，用来统一、缓存、管理 Logger 对象。

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.failure(ex.getErrorCode(), ex.getMessage()));
    }
//    处理你自己定义的异常：BusinessException 返回 400（🚨 Bad Request） 返回的 JSON 使用 ApiResponse.failure
//    在 Controller 里写：throw new BusinessException(ErrorCode.AUTH_FAILED, "密码不正确");
//    前端收到：
//    {
//      "success": false,
//      "code": "A0301",
//      "message": "密码不正确",
//      "timestamp": "...",
//      "data": null
//    }

    /**
     * 处理参数校验异常（前端传参数错了）
     * 这个异常来自方法参数校验，比如：
     * public record UserRequest(@NotNull String username, @Min(1) int age) {}
     * 如果前端传的 age < 1，Spring 会抛 MethodArgumentNotValidException
     * 你的异常处理器会：
     * ✔ 把所有字段错误拼成字符串
     * ✔ 返回友好的报错消息给前端
     * ✔ 使用 ErrorCode.VALIDATION_ERROR（参数错误）
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException ex) {
        String collect = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(GlobalExceptionHandler::formatFieldError)
                .collect(Collectors.joining("; "));
        return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.VALIDATION_ERROR, collect));
    }

    /**
     * 这个异常一般来自数据库，如：
     * 唯一约束冲突（如用户名重复）
     * 外键约束不满足
     * 它会统一返回「数据不满足约束条件」。
     * @param ex
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityException(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation", ex);
        return ResponseEntity.badRequest().body(ApiResponse.failure(ErrorCode.VALIDATION_ERROR, "数据不满足约束条件"));
    }

    /**
     * 作用类似：
     * 任何没有匹配到的异常，都跑到这里来。
     * 比如 NullPointerException、数组越界等都走这里。
     * 返回：
     * HTTP 500
     * ErrorCode.INTERNAL_ERROR
     * 防止异常堆栈直接暴露给前端（不安全）
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        log.error("Unhandled server error", ex);
        return ResponseEntity.internalServerError().body(ApiResponse.failure(ErrorCode.INTERNAL_ERROR));
    }

    private static String formatFieldError(FieldError error) {
        return error.getField() + " " + error.getDefaultMessage();
    }
}
