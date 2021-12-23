package top.homesoft.framework.log.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.homesoft.framework.log.aspectj.SystemLogAspect;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;
import top.homesoft.framework.log.domain.systemlog.repository.impl.SystemLogRepositoryImpl;


@Configuration
@Import(value = SystemLogAspect.class)
@MapperScan("top.homesoft.framework.log.domain.systemlog.repository.mapper")
public class SystemLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SystemLogRepository.class)
    public SystemLogRepository systemLogRepository() {
        SystemLogRepository systemLogRepository = new SystemLogRepositoryImpl();
        return  systemLogRepository;
    }
}
