package com.yx.circle.common.security;

import com.yx.circle.user.dto.UserProfileDto;

public final class UserHolder {

    private static final ThreadLocal<UserProfileDto> USER_THREAD_LOCAL = new ThreadLocal<>();

    private UserHolder() {
    }

    public static void saveUser(UserProfileDto user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static UserProfileDto getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}
