package top.homesoft.framework.rabbit;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
@Import({MQTemplateConfigurationSelector.class})
@Configuration
public @interface EnableMQTemplate {
}
