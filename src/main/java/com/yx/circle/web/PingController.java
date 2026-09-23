package com.yx.circle.web;

import com.yx.circle.common.api.ApiResult;
import com.yx.circle.common.api.ResultCode;
import com.yx.circle.common.exception.BizException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "System")
@RestController
@RequestMapping("/api/v1")
public class PingController {

    @Operation(summary = "Health ping")
    @GetMapping("/ping")
    public ApiResult<String> ping() {
        return ApiResult.ok("pong");
    }

    @Operation(summary = "Trigger sample business error")
    @GetMapping("/ping/error-demo")
    public ApiResult<Void> errorDemo() {
        throw new BizException(ResultCode.BIZ_ERROR, "demo business failure");
    }
}
