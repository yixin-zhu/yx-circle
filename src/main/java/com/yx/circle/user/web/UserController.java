package com.yx.circle.user.web;

import com.yx.circle.common.api.ApiResult;
import com.yx.circle.common.security.UserHolder;
import com.yx.circle.user.dto.UserProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Operation(summary = "Current logged-in user profile")
    @GetMapping("/me")
    public ApiResult<UserProfileDto> me() {
        return ApiResult.ok(UserHolder.getUser());
    }
}
