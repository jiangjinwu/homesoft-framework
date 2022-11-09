package com.jubaozan.service.commission.datasource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataSourceConfig {

//    @Primary
//    @Bean
//    @ConfigurationProperties("spring.datasource.druid")
//    public DataSource dataSourceOne(){
//        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
//        List<Filter> proxyFilters = new ArrayList<>();
//        proxyFilters.add(logFilter());
//        druidDataSource.setProxyFilters(proxyFilters);
//        return druidDataSource;
//    }
//
//    @Bean
//    public Filter logFilter(){
//        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
//        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
//        return slf4jLogFilter;
//    }
}
