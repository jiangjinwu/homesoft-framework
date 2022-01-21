package top.homesoft.framework.rabbit.support;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitTemplateConfig  implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息唯一标识："+correlationData);
        System.out.println("确认结果："+ack);
        System.out.println("失败原因："+cause);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("message："+message);
        System.out.println("s："+s);
        System.out.println("s1："+s1);
        System.out.println("s1："+s2);
    }
}
