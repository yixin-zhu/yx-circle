package com.yx.circle.circle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.circle.circle.dto.CircleDetailVo;
import com.yx.circle.entity.CircleMember;

import java.util.List;

public interface CircleMemberService extends IService<CircleMember> {

    boolean isJoined(Long circleId, Long userId);

    void join(Long circleId, Long userId);

    List<CircleDetailVo> listJoinedCircles(Long userId);
}
