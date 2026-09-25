package com.yx.circle.circle.support;

import com.yx.circle.common.api.ResultCode;
import com.yx.circle.common.exception.BizException;
import com.yx.circle.entity.Circle;
import com.yx.circle.mapper.CircleMapper;
import com.yx.circle.mapper.CircleMemberMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yx.circle.entity.CircleMember;
import org.springframework.stereotype.Component;

@Component
public class CircleAccessHelper {

    private final CircleMapper circleMapper;
    private final CircleMemberMapper circleMemberMapper;

    public CircleAccessHelper(CircleMapper circleMapper, CircleMemberMapper circleMemberMapper) {
        this.circleMapper = circleMapper;
        this.circleMemberMapper = circleMemberMapper;
    }

    public Circle requireCircle(Long circleId) {
        Circle circle = circleMapper.selectById(circleId);
        if (circle == null) {
            throw new BizException(ResultCode.NOT_FOUND, "星球不存在");
        }
        return circle;
    }

    public void requireHost(Circle circle, Long userId) {
        if (!circle.getHostId().equals(userId)) {
            throw new BizException(ResultCode.FORBIDDEN, "仅星主可操作");
        }
    }

    public boolean isMemberOrHost(Circle circle, Long userId) {
        if (circle.getHostId().equals(userId)) {
            return true;
        }
        Long count = circleMemberMapper.selectCount(new LambdaQueryWrapper<CircleMember>()
                .eq(CircleMember::getCircleId, circle.getId())
                .eq(CircleMember::getUserId, userId)
                .eq(CircleMember::getStatus, 1));
        return count != null && count > 0;
    }

    public void requireMember(Circle circle, Long userId) {
        if (!isMemberOrHost(circle, userId)) {
            throw new BizException(ResultCode.FORBIDDEN, "仅星球成员可访问");
        }
    }
}
