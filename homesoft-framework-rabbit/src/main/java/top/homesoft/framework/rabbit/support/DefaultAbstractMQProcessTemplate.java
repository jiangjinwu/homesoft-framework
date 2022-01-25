package top.homesoft.framework.rabbit.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import top.homesoft.framework.mq.bean.BaseMessage;

public class DefaultAbstractMQProcessTemplate<T extends BaseMessage> extends AbstractMQProcessTemplate {

    final PlayBackMessageRepository playBackMessageRepository;

    protected final Log logger = LogFactory.getLog(this.getClass());

    public DefaultAbstractMQProcessTemplate(PlayBackMessageRepository playBackMessageRepository) {
        this.playBackMessageRepository = playBackMessageRepository;
    }

    @Override
    protected void persistMessage(BaseMessage message) {
        playBackMessageRepository.put(message);
    }

    @Override
    protected void finishPlayBackMessage(String messageId) {
        playBackMessageRepository.delete(messageId);
    }

}
