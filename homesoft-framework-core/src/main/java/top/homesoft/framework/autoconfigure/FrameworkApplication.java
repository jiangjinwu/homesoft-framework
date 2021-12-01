package top.homesoft.framework.autoconfigure;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.homesoft.framework.config.web.WebMvcConfig;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
@SpringBootApplication
@Import(WebMvcConfig.class)
@Configuration
public @interface FrameworkApplication {
}
