package com.scuse.volunteerhub.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfigure implements WebMvcConfigurer {
//    @Autowired
//    private LoginCheckInterceptor loginCheckInterceptor;
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginCheckInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/login");
//    }

    /**
     * 将全局接收的前端发来的日期字符串自动转换为日期格式
     *
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldType(LocalDate.class, new USLocalDateFormatter());
    }

    private static class USLocalDateFormatter implements org.springframework.format.Formatter<LocalDate> {
        @Override
        public LocalDate parse(String text, java.util.Locale locale) {
            return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
        }

        @Override
        public String print(LocalDate object, java.util.Locale locale) {
            return DateTimeFormatter.ISO_DATE.format(object);
        }
    }
}
