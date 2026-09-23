package com.yx.circle.common.util;

import java.util.regex.Pattern;

public final class PhoneUtils {

    private static final Pattern MOBILE = Pattern.compile("^1[3-9]\\d{9}$");

    private PhoneUtils() {
    }

    public static boolean isInvalid(String phone) {
        return phone == null || !MOBILE.matcher(phone).matches();
    }
}
