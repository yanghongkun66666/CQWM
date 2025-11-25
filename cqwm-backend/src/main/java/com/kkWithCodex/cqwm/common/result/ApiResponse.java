package com.kkWithCodex.cqwm.common.result;

import java.time.Instant;

/**
 * REST response wrapper to keep the contract consistent across modules.
 * ApiResponse + ErrorCode 定义统一响应
 */
public class ApiResponse<T> {

    private final boolean success;
    private final String code;
    private final String message;
    private final Instant timestamp;
    private final T data;

    private ApiResponse(boolean success, String code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = Instant.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, ErrorCode.SUCCESS.getCode(), ErrorCode.SUCCESS.getMessage(), data);
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode, String overrideMessage) {
        return new ApiResponse<>(false, errorCode.getCode(),
                overrideMessage != null ? overrideMessage : errorCode.getMessage(), null);
    }

    public static <T> ApiResponse<T> failure(ErrorCode errorCode) {
        return failure(errorCode, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public T getData() {
        return data;
    }
}
