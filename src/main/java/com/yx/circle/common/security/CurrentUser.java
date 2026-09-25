package com.yx.circle.common.security;

import com.yx.circle.common.api.ResultCode;
import com.yx.circle.common.exception.BizException;
import com.yx.circle.user.dto.UserProfileDto;

public final class CurrentUser {

    private CurrentUser() {
    }

    public static UserProfileDto require() {
        UserProfileDto user = UserHolder.getUser();
        if (user == null) {
            throw new BizException(ResultCode.UNAUTHORIZED);
        }
        return user;
    }

    public static Long requireUserId() {
        return require().getId();
    }
}
