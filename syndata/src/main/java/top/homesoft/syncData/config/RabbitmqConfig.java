package top.homesoft.syncData.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;
import top.homesoft.syncData.mq.consumer.DynamicDataConsumer;
import top.homesoft.syncData.mybatis.RabbitQueueDTO;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Generating Your Own Metadata by Using the Annotation Processor
 * https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/reference/html/appendix-configuration-metadata.html#configuration-metadata-annotation-processor
 */

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitmqConfig {

    private String exchangeName;

    private List<RabbitQueueDTO> queues;

    @Resource(name = "rabbitConnectionFactory")
    private ConnectionFactory connectionFactory;

    private final DynamicDataConsumer dynamicDataConsumer;

    private final ListenerConfig listenerConfig;

    public RabbitmqConfig(DynamicDataConsumer dynamicDataConsumer, ListenerConfig listenerConfig) {
        this.dynamicDataConsumer = dynamicDataConsumer;
        this.listenerConfig = listenerConfig;
    }


    @Bean
    @Order(2)
    public SimpleMessageListenerContainer mqMessageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(this.connectionFactory);
        if (CollectionUtils.isEmpty(this.listenerConfig.getQueues())) {
            logger.error("");
                    System.exit(-1);
        }
        String[] queueNames = (String[])this.listenerConfig.getQueues().stream().map(RabbitQueueDTO::getQueueName).toArray(x$0 -> new String[x$0]);
        container.setQueueNames(queueNames);
        container.setExposeListenerChannel(true);
        container.setPrefetchCount(this.listenerConfig.getPrefetchCount());
        container.setConcurrentConsumers(this.listenerConfig.getConcurrentConsumers());
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(this.dynamicDataConsumer);
        return container;
    }
    @PostConstruct
    public void initCfg() throws IOException {
        Connection conn = null;
        Channel channel = null;
        try {
            conn = this.connectionFactory.createConnection();
            channel = conn.createChannel(false);
            initRabbitQueueConfig(channel, this.exchangeName, this.queues);
            initRabbitQueueConfig(channel, this.listenerConfig.getExchangeName(), this.listenerConfig.getQueues());
        } finally {
            if (null != channel)
                try {
                    channel.close();
                } catch (TimeoutException e) {
                    logger.error("channel close exception", e);
                }
            assert conn != null;
            conn.close();
        }
    }

    private void initRabbitQueueConfig(Channel channel, String exchangeName, List<RabbitQueueDTO> queues) throws IOException {
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, null);
        queues.forEach(queue -> {
            try {
                channel.queueDeclare(queue.getQueueName(), true, false, false, null);
            } catch (IOException e) {
                logger.error("Queue configuration fail, please check configuration");
                System.exit(-1);
            }

            //TODO
           // queue.getRouteKeys().forEach(());
        });
    }
}
