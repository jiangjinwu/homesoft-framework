package com.jubaozan.service.commission;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.jubaozan.service.commission.domain.**.mapper"})
public class CommissionApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommissionApplication.class, args);
    }
}
