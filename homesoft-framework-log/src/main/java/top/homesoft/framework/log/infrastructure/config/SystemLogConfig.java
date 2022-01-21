package top.homesoft.framework.log.infrastructure.config;

import lombok.Data;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.homesoft.framework.log.aspectj.ControllerSystemLogAspect;
import top.homesoft.framework.log.domain.systemlog.repository.SystemLogRepository;
import top.homesoft.framework.log.domain.systemlog.repository.impl.SystemLogRepositoryImpl;

import java.util.Set;


@Configuration
@ConfigurationProperties(value = "hs.log")
@Data
public class SystemLogConfig {

     String basePackage;

	 String requestIdKey;

	 Header header;

	 @Data
	 public static class Header{
		 String userIdKey;
		 Set<String> excludes;
	 }
}
