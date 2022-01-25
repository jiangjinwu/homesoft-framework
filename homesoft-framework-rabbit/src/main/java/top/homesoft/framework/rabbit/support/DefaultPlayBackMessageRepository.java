package top.homesoft.framework.rabbit.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import top.homesoft.framework.mq.bean.BaseMessage;

public class DefaultPlayBackMessageRepository<T extends BaseMessage> implements PlayBackMessageRepository {

    final RMapCache<String, BaseMessage> messageRMapCache;

    protected final Log logger = LogFactory.getLog(this.getClass());

    public DefaultPlayBackMessageRepository(RedissonClient redissonClient, JsonJacksonCodec jsonJacksonCodec) {
        this.messageRMapCache = redissonClient.getMapCache("HOMESOFT:MESSAGE", jsonJacksonCodec, LocalCachedMapOptions.defaults());
    }

    @Override
    public BaseMessage put(BaseMessage message) {
        return messageRMapCache.put(message.getId(), message);
    }

    @Override
    public BaseMessage get(String messageId) {
        return messageRMapCache.get(messageId);
    }

    @Override
    public BaseMessage delete(String messageId) {
        return messageRMapCache.remove(messageId);
    }


}
