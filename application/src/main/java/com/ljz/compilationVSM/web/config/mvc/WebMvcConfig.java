package com.ljz.compilationVSM.web.config.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;

/**
 * Web MVC 配置类
 *
 * @author ljz
 * @since 2024-12-26
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域配置
     *
     * @param registry 注册参数
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLongConverter());
        registry.addConverter(new LongToStringConverter());
    }

    public static class StringToLongConverter implements Converter<String, Long> {
        @Override
        public Long convert(@Nullable String source) {
            return Objects.isNull(source) ? null : Long.parseLong(source);
        }
    }

    public static class LongToStringConverter implements Converter<Long, String> {
        @Override
        public String convert(@Nullable Long source) {
            return Objects.isNull(source) ? null : source.toString();
        }
    }

}