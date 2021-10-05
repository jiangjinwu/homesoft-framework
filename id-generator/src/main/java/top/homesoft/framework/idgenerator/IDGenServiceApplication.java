package top.homesoft.framework.idgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @author yuanjie
 */

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties({})
public class IDGenServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(IDGenServiceApplication.class, args);
    }
}


