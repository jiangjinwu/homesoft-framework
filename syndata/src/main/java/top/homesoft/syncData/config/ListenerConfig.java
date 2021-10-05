package top.homesoft.syncData.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.homesoft.syncData.mybatis.RabbitQueueDTO;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "rabbitmq.listener")
@Data
public class ListenerConfig {

    private String exchangeName;

    private int prefetchCount;

    private int concurrentConsumers;

    private List<RabbitQueueDTO> queues;

}
