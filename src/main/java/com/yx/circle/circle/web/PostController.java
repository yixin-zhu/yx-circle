package com.yx.circle.circle.web;

import com.yx.circle.circle.dto.CreatePostRequest;
import com.yx.circle.circle.service.PostService;
import com.yx.circle.common.api.ApiResult;
import com.yx.circle.common.api.PageResult;
import com.yx.circle.common.security.CurrentUser;
import com.yx.circle.entity.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post")
@RestController
@RequestMapping("/api/v1/circles/{circleId}/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "Page posts in circle (members only)")
    @GetMapping
    public ApiResult<PageResult<Post>> page(
            @PathVariable Long circleId,
            @RequestParam(defaultValue = "1") long current,
            @RequestParam(defaultValue = "10") long size) {
        return ApiResult.ok(postService.pageByCircle(circleId, current, size, CurrentUser.requireUserId()));
    }

    @Operation(summary = "Post detail (members only)")
    @GetMapping("/{postId}")
    public ApiResult<Post> detail(@PathVariable Long circleId, @PathVariable Long postId) {
        return ApiResult.ok(postService.getPost(circleId, postId, CurrentUser.requireUserId()));
    }

    @Operation(summary = "Create post (members only)")
    @PostMapping
    public ApiResult<Long> create(
            @PathVariable Long circleId,
            @RequestBody CreatePostRequest request) {
        return ApiResult.ok(postService.createPost(circleId, request, CurrentUser.requireUserId()));
    }
}
