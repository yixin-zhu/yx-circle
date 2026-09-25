package com.yx.circle.circle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.circle.circle.dto.CreatePostRequest;
import com.yx.circle.common.api.PageResult;
import com.yx.circle.entity.Post;

public interface PostService extends IService<Post> {

    PageResult<Post> pageByCircle(Long circleId, long current, long size, Long userId);

    Post getPost(Long circleId, Long postId, Long userId);

    Long createPost(Long circleId, CreatePostRequest request, Long userId);
}
