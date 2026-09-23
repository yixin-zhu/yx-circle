package com.yx.circle.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private int code;
    private String message;
    private T data;

    public ApiResult() {
    }

    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static ApiResult<Void> ok() {
        return ok(null);
    }

    public static ApiResult<Void> fail(ResultCode resultCode) {
        return new ApiResult<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    public static ApiResult<Void> fail(ResultCode resultCode, String message) {
        return new ApiResult<>(resultCode.getCode(), message, null);
    }

    public static ApiResult<Void> fail(int code, String message) {
        return new ApiResult<>(code, message, null);
    }
}
