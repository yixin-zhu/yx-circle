package com.yx.circle.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResult<T> {

    private int code;
    private String message;
    private T data;

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
