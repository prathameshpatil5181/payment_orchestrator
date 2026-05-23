package com.orbyte.router.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutingCacheHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    public Object getFromCache(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
