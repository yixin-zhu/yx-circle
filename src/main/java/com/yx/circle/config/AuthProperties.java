package com.yx.circle.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "yx-circle.auth")
public class AuthProperties {

    /** Mock SMS: fixed code in dev; still written to Redis for login validation. */
    private String mockSmsCode = "123456";
    private long loginCodeTtlMinutes = 2;
    private long tokenTtlMinutes = 360;

    public String getMockSmsCode() {
        return mockSmsCode;
    }

    public void setMockSmsCode(String mockSmsCode) {
        this.mockSmsCode = mockSmsCode;
    }

    public long getLoginCodeTtlMinutes() {
        return loginCodeTtlMinutes;
    }

    public void setLoginCodeTtlMinutes(long loginCodeTtlMinutes) {
        this.loginCodeTtlMinutes = loginCodeTtlMinutes;
    }

    public long getTokenTtlMinutes() {
        return tokenTtlMinutes;
    }

    public void setTokenTtlMinutes(long tokenTtlMinutes) {
        this.tokenTtlMinutes = tokenTtlMinutes;
    }
}
