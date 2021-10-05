package top.homesoft.syncData.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;
import top.homesoft.syncData.mq.domain.DataMessage;

import java.util.Optional;

@Slf4j
@Component
public class DynamicDataConsumer implements ChannelAwareMessageListener {
    private final top.homesoft.syncData.decorator.SyncDataDecorator syncDataDecorator;

    public DynamicDataConsumer(top.homesoft.syncData.decorator.SyncDataDecorator syncDataDecorator) {
        this.syncDataDecorator = syncDataDecorator;
    }

    public void onMessage(Message message, Channel channel) {
        String reqBody = Optional.<Message>ofNullable(message).map(m -> new String(m.getBody())).orElse(null);
        try {
            DataMessage dataMessage = fromJson(reqBody);
            if (logger.isDebugEnabled())
                logger.debug("{}", dataMessage);
            this.syncDataDecorator.parseHandle(dataMessage);
        } catch (Exception ex) {
            logger.error("{}", reqBody, ex);
        }
    }

    public DataMessage fromJson(String message) {
        return (DataMessage) JSON.parseObject(message, DataMessage.class, new com.alibaba.fastjson.parser.Feature[0]);
    }
}
