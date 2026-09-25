package com.yx.circle.circle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.circle.circle.dto.CircleDetailVo;
import com.yx.circle.circle.dto.CreateCircleRequest;
import com.yx.circle.circle.dto.UpdateCircleRequest;
import com.yx.circle.common.api.PageResult;
import com.yx.circle.entity.Circle;

public interface CircleService extends IService<Circle> {

    PageResult<CircleDetailVo> pageCircles(Long categoryId, long current, long size);

    CircleDetailVo getDetail(Long circleId, Long userId);

    Long createCircle(CreateCircleRequest request, Long hostUserId);

    void updateCircle(Long circleId, UpdateCircleRequest request, Long userId);
}
