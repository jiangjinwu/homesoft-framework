package top.homesoft.framework.rabbit.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import top.homesoft.framework.mq.bean.BaseMessage;

import java.util.Objects;

public class DefaultPlayBackMessageRepository<T extends BaseMessage> implements PlayBackMessageRepository {

    final RMapCache<String, RMapCache> messageRMapCacheRMap;
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected final RedissonClient redissonClient;
    protected final JsonJacksonCodec jsonJacksonCodec;
    public DefaultPlayBackMessageRepository(RedissonClient redissonClient, JsonJacksonCodec jsonJacksonCodec) {
        this.redissonClient = redissonClient;
        this.jsonJacksonCodec = jsonJacksonCodec;
        this.messageRMapCacheRMap = redissonClient.getMapCache("HOMESOFT:MESSAGE2", jsonJacksonCodec, LocalCachedMapOptions.defaults());
    }

    public RMapCache<String, BaseMessage>  getCacheMap(String bizCode){
        RMapCache<String, BaseMessage> mapCache=   messageRMapCacheRMap.get(bizCode);
        if(Objects.isNull(mapCache)){
            mapCache =redissonClient.getMapCache("HOMESOFT:MESSAGE2"+":"+bizCode, jsonJacksonCodec, LocalCachedMapOptions.defaults());
        }
        return mapCache;
    }

    @Override
    public BaseMessage put(BaseMessage message) {
        RMapCache<String,BaseMessage> mapCache= getCacheMap(message.getBizCode());
        return mapCache.put(message.getId(), message);
    }

    @Override
    public BaseMessage get(String bizCode,String messageId) {
        RMapCache<String,BaseMessage> mapCache= messageRMapCacheRMap.get(bizCode);
        return mapCache.get(messageId);
    }

    @Override
    public BaseMessage delete(String bizCode,String messageId) {
        RMapCache<String,BaseMessage> mapCache= messageRMapCacheRMap.get(bizCode);
        return mapCache.remove(messageId);
    }


}
