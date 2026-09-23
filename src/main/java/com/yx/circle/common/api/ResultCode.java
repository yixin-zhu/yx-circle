package com.yx.circle.common.api;

import lombok.Getter;

@Getter
public enum ResultCode {

    SUCCESS(0, "ok"),
    BAD_REQUEST(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not found"),
    BIZ_ERROR(1000, "business error"),
    INTERNAL_ERROR(500, "internal server error");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
