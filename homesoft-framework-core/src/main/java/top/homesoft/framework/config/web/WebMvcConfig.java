package top.homesoft.framework.config.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import top.homesoft.framework.http.intercepter.LogFilter;
import top.homesoft.framework.http.intercepter.LogInterceptor;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Map<String, String> resourceMap = new HashMap<>(16);
        resourceMap.put("swagger-ui.html", "classpath:/META-INF/resources/");
        resourceMap.put("/webjars/**", "classpath:/META-INF/resources/webjars/");
        resourceMap.put("/static/**", "classpath:/static/");
        resourceMap.put("/doc.html", "classpath*:/META-INF/resources/");
        resourceMap.forEach((pattern, location) -> {
            logger.info("添加静态资源: {} -> {}", pattern, location);
            registry.addResourceHandler(pattern)
                    .addResourceLocations(location);
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration  = registry.addInterceptor(logInterceptor());
    }


    @Bean
    public LogInterceptor logInterceptor(){
        return new LogInterceptor();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {

        LogFilter crosFilter = null;
        try {
            crosFilter = new LogFilter();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FilterRegistrationBean bean = new FilterRegistrationBean(crosFilter);
        return bean;
    }



}
