package com.kkWithCodex.cqwm.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * Resolves the client IP considering load balancer headers.
 * IpUtils 提取客户端 IP（用于登录日志字段）
 */
public final class IpUtils {

    private static final String UNKNOWN = "unknown";

    private IpUtils() {
    }

    public static String resolveClientIp(HttpServletRequest request) {
        String[] headerCandidates = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headerCandidates) {
            String ip = request.getHeader(header);
            if (StringUtils.hasText(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                // handle multiple proxies scenario
                return ip.split(",")[0].trim();
            }
        }
        return request.getRemoteAddr();
    }
}
