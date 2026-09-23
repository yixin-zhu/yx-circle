package com.yx.circle.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.circle.entity.User;

public interface UserService extends IService<User> {

    void sendLoginCode(String phone);

    String login(String phone, String code);
}
