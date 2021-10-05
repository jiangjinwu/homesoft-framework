package top.homesoft.syncData.mq.producer;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.homesoft.syncData.vo.SyncDataMessage;

@Component
@Slf4j
public class SyncDataProducer {

    @Value("${rabbitmq.exchangeName}")
    private String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    public SyncDataProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendSyncDataMessage(SyncDataMessage message, String routeKey) {
       // String json = GsonUtil.toJson(message);
        String json = JSON.toJSONString(message);
        logger.debug("{},{}", routeKey, json);
        this.rabbitTemplate.convertAndSend(this.exchangeName, routeKey, json);
    }
}
