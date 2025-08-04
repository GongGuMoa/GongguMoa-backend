package com.gonggumoa.global.context;

import com.gonggumoa.domain.user.entity.User;
import com.gonggumoa.global.security.CustomUserDetails;

public class UserContext {
    private static final ThreadLocal<CustomUserDetails> USER_DETAILS = new ThreadLocal<>();

    public static void set(CustomUserDetails userDetails) {
        if (userDetails != null) {
            USER_DETAILS.set(userDetails);
        }
    }

    public static Long getUserId() {
        return USER_DETAILS.get().getUserId();
    }

    public static User getUser() {
        return USER_DETAILS.get().getUser();
    }

    public static void clear() {
        USER_DETAILS.remove();
    }
}
