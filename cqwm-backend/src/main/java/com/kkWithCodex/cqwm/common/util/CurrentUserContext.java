package com.kkWithCodex.cqwm.common.util;

/**
 * ThreadLocal holder for current username (set in JWT filter).
 */
public final class CurrentUserContext {

    private static final ThreadLocal<String> USERNAME_HOLDER = new ThreadLocal<>();

    private CurrentUserContext() {
    }

    public static void setUsername(String username) {
        USERNAME_HOLDER.set(username);
    }

    public static String getUsername() {
        return USERNAME_HOLDER.get();
    }

    public static void clear() {
        USERNAME_HOLDER.remove();
    }
}
