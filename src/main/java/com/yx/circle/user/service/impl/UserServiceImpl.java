package com.yx.circle.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.circle.common.api.ResultCode;
import com.yx.circle.common.exception.BizException;
import com.yx.circle.common.util.PhoneUtils;
import com.yx.circle.config.AuthProperties;
import com.yx.circle.entity.User;
import com.yx.circle.mapper.UserMapper;
import com.yx.circle.user.dto.UserProfileDto;
import com.yx.circle.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.yx.circle.common.constant.RedisConstants.LOGIN_CODE_KEY;
import static com.yx.circle.common.constant.RedisConstants.LOGIN_USER_KEY;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final String NICK_NAME_PREFIX = "member_";

    private final StringRedisTemplate stringRedisTemplate;
    private final AuthProperties authProperties;

    public UserServiceImpl(StringRedisTemplate stringRedisTemplate, AuthProperties authProperties) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.authProperties = authProperties;
    }

    @Override
    public void sendLoginCode(String phone) {
        if (PhoneUtils.isInvalid(phone)) {
            throw new BizException(ResultCode.BAD_REQUEST, "手机号格式错误");
        }
        String code = authProperties.getMockSmsCode();
        stringRedisTemplate.opsForValue().set(
                LOGIN_CODE_KEY + phone,
                code,
                authProperties.getLoginCodeTtlMinutes(),
                TimeUnit.MINUTES
        );
        log.info("[mock-sms] phone={} code={} ttl={}min", phone, code, authProperties.getLoginCodeTtlMinutes());
    }

    @Override
    public String login(String phone, String code) {
        if (PhoneUtils.isInvalid(phone)) {
            throw new BizException(ResultCode.BAD_REQUEST, "手机号格式错误");
        }
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        if (cacheCode == null || !cacheCode.equals(code)) {
            throw new BizException(ResultCode.BAD_REQUEST, "验证码错误");
        }

        User user = lambdaQuery().eq(User::getPhone, phone).one();
        if (user == null) {
            user = createUserWithPhone(phone);
        }

        UserProfileDto profile = toProfileDto(user);
        String token = UUID.randomUUID().toString();
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, toRedisHash(profile));
        stringRedisTemplate.expire(tokenKey, authProperties.getTokenTtlMinutes(), TimeUnit.MINUTES);

        stringRedisTemplate.delete(LOGIN_CODE_KEY + phone);
        return token;
    }

    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(NICK_NAME_PREFIX + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        user.setRole(0);
        save(user);
        return user;
    }

    static UserProfileDto toProfileDto(User user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setPhone(user.getPhone());
        dto.setNickName(user.getNickName());
        dto.setIcon(user.getIcon());
        dto.setRole(user.getRole());
        return dto;
    }

    static Map<String, String> toRedisHash(UserProfileDto profile) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(profile.getId()));
        map.put("phone", profile.getPhone());
        map.put("nickName", profile.getNickName());
        if (profile.getIcon() != null) {
            map.put("icon", profile.getIcon());
        }
        map.put("role", String.valueOf(profile.getRole()));
        return map;
    }

    public static UserProfileDto fromRedisHash(Map<Object, Object> entries) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(Long.parseLong(String.valueOf(entries.get("id"))));
        dto.setPhone(String.valueOf(entries.get("phone")));
        dto.setNickName(String.valueOf(entries.get("nickName")));
        if (entries.get("icon") != null) {
            dto.setIcon(String.valueOf(entries.get("icon")));
        }
        dto.setRole(Integer.parseInt(String.valueOf(entries.get("role"))));
        return dto;
    }
}
