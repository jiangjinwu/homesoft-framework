package top.homesoft.framework.autoconfigure;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.homesoft.framework.config.web.WebMvcConfig;

import java.lang.annotation.*;



@Import(WebMvcConfig.class)
@Configuration
public class FrameworkAutoConfiguration {
}
