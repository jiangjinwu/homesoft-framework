package top.homesoft.drools;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.homesoft.drools.infrastructure.ext.dao")
public class DroolsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DroolsDemoApplication.class, args);
    }
}
