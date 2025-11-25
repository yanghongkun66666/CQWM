package com.kkWithCodex.cqwm.common.exception;

import com.kkWithCodex.cqwm.common.result.ErrorCode;

/**
 * Custom runtime exception carrying a platform error code.
 * BusinessException + GlobalExceptionHandler 负责抛出业务错误（如登录失败、账户停用）
 * 并转成 {success:false,code,message} 的 JSON
 */
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
