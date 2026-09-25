package com.yx.circle.circle.web;

import com.yx.circle.circle.dto.CircleDetailVo;
import com.yx.circle.circle.dto.CreateCircleRequest;
import com.yx.circle.circle.dto.UpdateCircleRequest;
import com.yx.circle.circle.service.CircleMemberService;
import com.yx.circle.circle.service.CircleService;
import com.yx.circle.common.api.ApiResult;
import com.yx.circle.common.api.PageResult;
import com.yx.circle.common.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Circle")
@RestController
@RequestMapping("/api/v1/circles")
public class CircleController {

    private final CircleService circleService;
    private final CircleMemberService circleMemberService;

    public CircleController(CircleService circleService, CircleMemberService circleMemberService) {
        this.circleService = circleService;
        this.circleMemberService = circleMemberService;
    }

    @Operation(summary = "Page open circles")
    @GetMapping
    public ApiResult<PageResult<CircleDetailVo>> page(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        return ApiResult.ok(circleService.pageCircles(categoryId, current, size));
    }

    @Operation(summary = "Circle detail")
    @GetMapping("/{id}")
    public ApiResult<CircleDetailVo> detail(@PathVariable Long id) {
        Long userId = CurrentUser.requireUserId();
        return ApiResult.ok(circleService.getDetail(id, userId));
    }

    @Operation(summary = "Create circle (current user as host)")
    @PostMapping
    public ApiResult<Long> create(@RequestBody CreateCircleRequest request) {
        Long id = circleService.createCircle(request, CurrentUser.requireUserId());
        return ApiResult.ok(id);
    }

    @Operation(summary = "Update circle (host only)")
    @PutMapping("/{id}")
    public ApiResult<Void> update(@PathVariable Long id, @RequestBody UpdateCircleRequest request) {
        circleService.updateCircle(id, request, CurrentUser.requireUserId());
        return ApiResult.ok();
    }

    @Operation(summary = "Whether current user joined the circle")
    @GetMapping("/{id}/membership")
    public ApiResult<Map<String, Boolean>> membership(@PathVariable Long id) {
        boolean joined = circleMemberService.isJoined(id, CurrentUser.requireUserId());
        return ApiResult.ok(Map.of("joined", joined));
    }

    @Operation(summary = "Join circle (mock paid join)")
    @PostMapping("/{id}/join")
    public ApiResult<Void> join(@PathVariable Long id) {
        circleMemberService.join(id, CurrentUser.requireUserId());
        return ApiResult.ok();
    }
}
