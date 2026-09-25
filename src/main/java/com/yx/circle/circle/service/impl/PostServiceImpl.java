package com.yx.circle.circle.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.circle.circle.dto.CreatePostRequest;
import com.yx.circle.circle.service.PostService;
import com.yx.circle.circle.support.CircleAccessHelper;
import com.yx.circle.common.api.PageResult;
import com.yx.circle.common.api.ResultCode;
import com.yx.circle.common.exception.BizException;
import com.yx.circle.entity.Circle;
import com.yx.circle.entity.Post;
import com.yx.circle.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final CircleAccessHelper circleAccessHelper;

    public PostServiceImpl(CircleAccessHelper circleAccessHelper) {
        this.circleAccessHelper = circleAccessHelper;
    }

    @Override
    public PageResult<Post> pageByCircle(Long circleId, long current, long size, Long userId) {
        Circle circle = circleAccessHelper.requireCircle(circleId);
        circleAccessHelper.requireMember(circle, userId);
        Page<Post> page = lambdaQuery()
                .eq(Post::getCircleId, circleId)
                .orderByDesc(Post::getId)
                .page(new Page<>(current, size));
        return new PageResult<>(page.getRecords(), page.getTotal(), page.getCurrent(), page.getSize());
    }

    @Override
    public Post getPost(Long circleId, Long postId, Long userId) {
        Circle circle = circleAccessHelper.requireCircle(circleId);
        circleAccessHelper.requireMember(circle, userId);
        Post post = getById(postId);
        if (post == null || !post.getCircleId().equals(circleId)) {
            throw new BizException(ResultCode.NOT_FOUND, "帖子不存在");
        }
        return post;
    }

    @Override
    public Long createPost(Long circleId, CreatePostRequest request, Long userId) {
        Circle circle = circleAccessHelper.requireCircle(circleId);
        circleAccessHelper.requireMember(circle, userId);
        if (!StringUtils.hasText(request.getTitle()) || !StringUtils.hasText(request.getContent())) {
            throw new BizException(ResultCode.BAD_REQUEST, "标题与内容不能为空");
        }
        Post post = new Post();
        post.setCircleId(circleId);
        post.setUserId(userId);
        post.setTitle(request.getTitle().trim());
        post.setContent(request.getContent().trim());
        post.setLiked(0);
        save(post);
        return post.getId();
    }
}
