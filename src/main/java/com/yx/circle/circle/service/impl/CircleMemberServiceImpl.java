package com.yx.circle.circle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.circle.circle.dto.CircleDetailVo;
import com.yx.circle.circle.service.CircleMemberService;
import com.yx.circle.circle.support.CircleAccessHelper;
import com.yx.circle.common.api.ResultCode;
import com.yx.circle.common.exception.BizException;
import com.yx.circle.entity.Circle;
import com.yx.circle.entity.CircleMember;
import com.yx.circle.mapper.CircleMapper;
import com.yx.circle.mapper.CircleMemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CircleMemberServiceImpl extends ServiceImpl<CircleMemberMapper, CircleMember>
        implements CircleMemberService {

    private final CircleAccessHelper circleAccessHelper;
    private final CircleMapper circleMapper;

    public CircleMemberServiceImpl(CircleAccessHelper circleAccessHelper, CircleMapper circleMapper) {
        this.circleAccessHelper = circleAccessHelper;
        this.circleMapper = circleMapper;
    }

    @Override
    public boolean isJoined(Long circleId, Long userId) {
        Circle circle = circleAccessHelper.requireCircle(circleId);
        return circleAccessHelper.isMemberOrHost(circle, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void join(Long circleId, Long userId) {
        Circle circle = circleAccessHelper.requireCircle(circleId);
        if (circle.getStatus() != null && circle.getStatus() != 1) {
            throw new BizException(ResultCode.BIZ_ERROR, "星球未开放加入");
        }
        if (circleAccessHelper.isMemberOrHost(circle, userId)) {
            return;
        }
        CircleMember member = new CircleMember();
        member.setCircleId(circleId);
        member.setUserId(userId);
        member.setStatus(1);
        member.setExpireAt(LocalDateTime.now().plusYears(1));
        save(member);

        circle.setMemberCount(circle.getMemberCount() == null ? 1 : circle.getMemberCount() + 1);
        circleMapper.updateById(circle);
    }

    @Override
    public List<CircleDetailVo> listJoinedCircles(Long userId) {
        List<CircleMember> memberships = list(new LambdaQueryWrapper<CircleMember>()
                .eq(CircleMember::getUserId, userId)
                .eq(CircleMember::getStatus, 1));
        return memberships.stream()
                .map(m -> circleMapper.selectById(m.getCircleId()))
                .filter(c -> c != null)
                .map(c -> {
                    CircleDetailVo vo = new CircleDetailVo();
                    vo.setId(c.getId());
                    vo.setCategoryId(c.getCategoryId());
                    vo.setHostId(c.getHostId());
                    vo.setName(c.getName());
                    vo.setCover(c.getCover());
                    vo.setDescription(c.getDescription());
                    vo.setJoinPrice(c.getJoinPrice());
                    vo.setMemberCount(c.getMemberCount());
                    vo.setStatus(c.getStatus());
                    vo.setCreateTime(c.getCreateTime());
                    vo.setJoined(true);
                    return vo;
                })
                .collect(Collectors.toList());
    }
}
