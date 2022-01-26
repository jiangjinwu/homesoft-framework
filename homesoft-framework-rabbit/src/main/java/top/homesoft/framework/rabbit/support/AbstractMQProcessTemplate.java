package top.homesoft.framework.rabbit.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.MessagingMessageConverter;
import top.homesoft.framework.mq.bean.BaseMessage;
import top.homesoft.framework.mq.bean.BaseMessageType;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class AbstractMQProcessTemplate<T extends BaseMessage> {

    protected final Log logger = LogFactory.getLog(this.getClass());
      MessagingMessageConverter messagingMessageConverter;

    protected AbstractMQProcessTemplate( ) {
        this.messagingMessageConverter = messagingMessageConverter;
    }

    public void execute(Message amqpMessage, Consumer<BaseMessage> filter) {
        BaseMessage baseMessage = null;
        try {
            org.springframework.messaging.Message<BaseMessage> messagingMessage = (org.springframework.messaging.Message<BaseMessage>) messagingMessageConverter.fromMessage(amqpMessage);
            baseMessage = messagingMessage.getPayload();
            baseMessage.setMessageProperties(amqpMessage.getMessageProperties());
            if (Objects.isNull(baseMessage.getMessageType())) {
                baseMessage.setMessageType(BaseMessageType.ORIGINAL);
            }
            filter.accept(baseMessage);
            if (baseMessage.getMessageType().equals(BaseMessageType.PLAYBACK)) {
                //标记为已处理
                finishPlayBackMessage(baseMessage.getBizCode(), baseMessage.getId());
            }

        } catch (Exception e) {
            handleNotProcessMesssage(baseMessage);
        }
    }


    protected abstract void persistMessage(BaseMessage message);

    protected abstract void finishPlayBackMessage(String bizCode, String messageId);

    void handleNotProcessMesssage(BaseMessage baseMessage) {

        try {
            baseMessage.setMessageType(BaseMessageType.PLAYBACK);
            persistMessage(baseMessage);
        } catch (Exception e) {
            logger.error("记录未处理消息异常", e);
        }
    }
}
