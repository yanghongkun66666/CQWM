package com.kkWithCodex.cqwm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Externalized JWT configuration (secret, expiration window, issuer).
 * 绑定 JWT 配置供生成 token 使用。
 */
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    /**
     * Shared secret for signing/verifying tokens.
     */
    private String secret;

    /**
     * Token lifetime in seconds.
     */
    private long expiration = 864000000;

    /**
     * Token issuer claim.
     */
    private String issuer = "CQWM-Admin";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
