package com.example.demo.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author whz
 * @date 2021/3/10 下午10:28
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     *
     * @param registry corsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3600)
                .allowCredentials(true);
    }
}