package com.yx.circle.circle.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.circle.circle.dto.CircleDetailVo;
import com.yx.circle.circle.dto.CreateCircleRequest;
import com.yx.circle.circle.dto.UpdateCircleRequest;
import com.yx.circle.circle.service.CircleService;
import com.yx.circle.circle.support.CircleAccessHelper;
import com.yx.circle.common.api.PageResult;
import com.yx.circle.common.api.ResultCode;
import com.yx.circle.common.exception.BizException;
import com.yx.circle.entity.Circle;
import com.yx.circle.mapper.CircleMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CircleServiceImpl extends ServiceImpl<CircleMapper, Circle> implements CircleService {

    private final CircleAccessHelper circleAccessHelper;

    public CircleServiceImpl(CircleAccessHelper circleAccessHelper) {
        this.circleAccessHelper = circleAccessHelper;
    }

    @Override
    public PageResult<CircleDetailVo> pageCircles(Long categoryId, long current, long size) {
        Page<Circle> page = lambdaQuery()
                .eq(categoryId != null, Circle::getCategoryId, categoryId)
                .eq(Circle::getStatus, 1)
                .orderByDesc(Circle::getId)
                .page(new Page<>(current, size));
        List<CircleDetailVo> records = page.getRecords().stream()
                .map(c -> toVo(c, null))
                .collect(Collectors.toList());
        return new PageResult<>(records, page.getTotal(), page.getCurrent(), page.getSize());
    }

    @Override
    public CircleDetailVo getDetail(Long circleId, Long userId) {
        Circle circle = circleAccessHelper.requireCircle(circleId);
        boolean joined = circleAccessHelper.isMemberOrHost(circle, userId);
        return toVo(circle, joined);
    }

    @Override
    public Long createCircle(CreateCircleRequest request, Long hostUserId) {
        validateCreate(request);
        Circle circle = new Circle();
        circle.setCategoryId(request.getCategoryId());
        circle.setHostId(hostUserId);
        circle.setName(request.getName().trim());
        circle.setCover(request.getCover());
        circle.setDescription(request.getDescription());
        circle.setJoinPrice(request.getJoinPrice() != null ? request.getJoinPrice() : 0L);
        circle.setMemberCount(0);
        circle.setStatus(1);
        save(circle);
        return circle.getId();
    }

    @Override
    public void updateCircle(Long circleId, UpdateCircleRequest request, Long userId) {
        Circle circle = circleAccessHelper.requireCircle(circleId);
        circleAccessHelper.requireHost(circle, userId);
        if (StringUtils.hasText(request.getName())) {
            circle.setName(request.getName().trim());
        }
        if (request.getCover() != null) {
            circle.setCover(request.getCover());
        }
        if (request.getDescription() != null) {
            circle.setDescription(request.getDescription());
        }
        if (request.getJoinPrice() != null) {
            circle.setJoinPrice(request.getJoinPrice());
        }
        if (request.getStatus() != null) {
            circle.setStatus(request.getStatus());
        }
        updateById(circle);
    }

    private void validateCreate(CreateCircleRequest request) {
        if (request.getCategoryId() == null || !StringUtils.hasText(request.getName())) {
            throw new BizException(ResultCode.BAD_REQUEST, "分类与名称不能为空");
        }
    }

    private CircleDetailVo toVo(Circle circle, Boolean joined) {
        CircleDetailVo vo = new CircleDetailVo();
        vo.setId(circle.getId());
        vo.setCategoryId(circle.getCategoryId());
        vo.setHostId(circle.getHostId());
        vo.setName(circle.getName());
        vo.setCover(circle.getCover());
        vo.setDescription(circle.getDescription());
        vo.setJoinPrice(circle.getJoinPrice());
        vo.setMemberCount(circle.getMemberCount());
        vo.setStatus(circle.getStatus());
        vo.setCreateTime(circle.getCreateTime());
        vo.setJoined(joined);
        return vo;
    }
}
