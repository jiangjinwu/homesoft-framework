package top.homesoft.framework.rabbit;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import top.homesoft.framework.rabbit.support.DefaultAbstractMQProcessTemplate;
import top.homesoft.framework.rabbit.support.RabbitTemplateConfig;

import java.util.ArrayList;
import java.util.List;

public class MQTemplateConfigurationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> imports = new ArrayList<>();
        imports.add(RabbitTemplateConfig.class.getName());
        imports.add(DefaultAbstractMQProcessTemplate.class.getName());
        return imports.toArray(new String[0]);
    }
}
