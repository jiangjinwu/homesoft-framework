package top.homesoft.framework.rabbit.support;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessagingMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RabbitTemplateConfig
  // implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback
{
   @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        //rabbitTemplate.setConfirmCallback(this);
    }

//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        System.out.println("消息唯一标识："+correlationData);
//        System.out.println("确认结果："+ack);
//        System.out.println("失败原因："+cause);
//    }
//
//    @Override
//    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
//        System.out.println("message："+message);
//        System.out.println("s："+s);
//        System.out.println("s1："+s1);
//        System.out.println("s1："+s2);
//    }

    @Bean
    @ConditionalOnMissingBean
    public JsonJacksonCodec jsonJacksonCodec() {
        //下面代码解决LocalDateTime序列化与反序列化不一致问题
        ObjectMapper mapper =   newObjectMapper();
        return new JsonJacksonCodec(mapper);
    }

    public ObjectMapper newObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        //LocalDatetime序列化
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDate.class,
            new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addDeserializer(LocalDateTime.class,
            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        timeModule.addSerializer(LocalDate.class,
            new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        timeModule.addSerializer(LocalDateTime.class,
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(timeModule);


        final ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        return  mapper;
    }

//    @Bean(name = "rabbitTemplate")
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper mapper) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        JavaTimeModule timeModule = new JavaTimeModule();
//        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter(mapper);
//        template.setMessageConverter(messageConverter);
//        return template;
//    }

    @Bean
    @ConditionalOnMissingBean
    PlayBackMessageRepository playBackMessageRepository(RedissonClient redissonClient, JsonJacksonCodec jsonJacksonCodec){
        PlayBackMessageRepository playBackMessageRepository = new DefaultPlayBackMessageRepository<>(redissonClient,jsonJacksonCodec);
        return playBackMessageRepository;
    }

//    @Bean
//    @ConditionalOnMissingBean
//    MessagingMessageConverter messagingMessageConverter(){
//
////        SimpleRabbitListenerContainerFactoryConfigurer simpleRabbitListenerContainerFactoryConfigurer;
////        simpleRabbitListenerContainerFactoryConfigurer.
//        return new MessagingMessageConverter();
//    }
}
