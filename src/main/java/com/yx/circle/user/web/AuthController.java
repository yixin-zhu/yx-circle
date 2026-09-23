package com.yx.circle.user.web;

import com.yx.circle.common.api.ApiResult;
import com.yx.circle.user.dto.LoginRequest;
import com.yx.circle.user.dto.SendCodeRequest;
import com.yx.circle.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Send login verification code (mock SMS)")
    @PostMapping("/code")
    public ApiResult<Void> sendCode(@RequestBody SendCodeRequest request) {
        userService.sendLoginCode(request.getPhone());
        return ApiResult.ok();
    }

    @Operation(summary = "Login with phone and code, returns session token")
    @PostMapping("/login")
    public ApiResult<String> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getPhone(), request.getCode());
        return ApiResult.ok(token);
    }
}
