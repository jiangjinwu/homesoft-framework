package top.homesoft.gateway.token;

import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class TokenRenewalService {
    RedisTemplate redisKeyValueTemplate;

//    void renewal(String tokenId, LocalDateTime expireTime){
//        redisKeyValueTemplate.opsForValue().getOperations().expire(tokenId,30, TimeUnit.MINUTES);
//    }
}
