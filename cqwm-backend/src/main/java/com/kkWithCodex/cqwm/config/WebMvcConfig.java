package com.kkWithCodex.cqwm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 它通过实现 WebMvcConfigurer 接口，重写 addCorsMappings 方法，为整个后端项目配置跨域策略。
 * 前端（如 Vue/React）访问后端接口时，不会因为跨域而被浏览器阻拦。
 * 允许任何来源以任何方式请求你的后端接口，并避免跨域问题。
 */

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
