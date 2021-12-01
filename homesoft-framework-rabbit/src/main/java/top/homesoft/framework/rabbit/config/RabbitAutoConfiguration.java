package top.homesoft.framework.rabbit.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnClass({RabbitTemplate.class})
@ConditionalOnProperty(
        prefix = "spring.rabbitmq",
        matchIfMissing = true
)
public class RabbitAutoConfiguration {

    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper mapper) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        JavaTimeModule timeModule = new JavaTimeModule();
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(mapper);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
