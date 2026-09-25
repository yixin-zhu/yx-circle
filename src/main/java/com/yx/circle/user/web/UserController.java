package com.yx.circle.user.web;

import com.yx.circle.circle.dto.CircleDetailVo;
import com.yx.circle.circle.service.CircleMemberService;
import com.yx.circle.common.api.ApiResult;
import com.yx.circle.common.security.CurrentUser;
import com.yx.circle.user.dto.UserProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CircleMemberService circleMemberService;

    public UserController(CircleMemberService circleMemberService) {
        this.circleMemberService = circleMemberService;
    }

    @Operation(summary = "Current logged-in user profile")
    @GetMapping("/me")
    public ApiResult<UserProfileDto> me() {
        return ApiResult.ok(CurrentUser.require());
    }

    @Operation(summary = "Circles joined by current user")
    @GetMapping("/me/circles")
    public ApiResult<List<CircleDetailVo>> myCircles() {
        return ApiResult.ok(circleMemberService.listJoinedCircles(CurrentUser.requireUserId()));
    }
}
