package top.homesoft.framework.log.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.homesoft.framework.log.aspectj.ControllerSystemLogAspect;

import top.homesoft.framework.log.aspectj.FeignLogAspect;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;
import top.homesoft.framework.log.domain.systemlog.repository.impl.SystemLogRepositoryImpl;
import top.homesoft.framework.log.domain.systemlog.service.SystemLogService;
import top.homesoft.framework.log.infrastructure.repository.SystemlogServiceImpl;


@Configuration
@Import(value = {ControllerSystemLogAspect.class,SystemLogConfig.class, FeignLogAspect.class})
@MapperScan("top.homesoft.framework.log.domain.systemlog.repository.mapper")
public class SystemLogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SystemLogRepository.class)
    public SystemLogRepository systemLogRepository() {
        SystemLogRepository systemLogRepository = new SystemLogRepositoryImpl();
        return  systemLogRepository;
    }

	@Bean
	@ConditionalOnMissingBean(SystemLogService.class)
	public SystemLogService systemLogService() {
		SystemLogService systemLogService = new SystemlogServiceImpl();
		return  systemLogService;
	}
}
