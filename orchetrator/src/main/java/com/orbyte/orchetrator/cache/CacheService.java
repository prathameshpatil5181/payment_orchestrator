package com.orbyte.orchetrator.cache;

import com.orbyte.orchetrator.dtos.ConfigKeyvalueDto;
import com.orbyte.orchetrator.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {


    private final RedisTemplate redisTemplate;

    private final ConfigRepository configRepository;


    @Cacheable(value="config",key="#key", unless = "#result==null")
    public String getConfigFromCache(String key){
        ConfigKeyvalueDto result = configRepository.findByName(key);
        return result != null ? result.getValue() : null;
    }

}
