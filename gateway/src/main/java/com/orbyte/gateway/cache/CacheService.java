package com.orbyte.gateway.cache;

import com.orbyte.gateway.dto.dtoimpl.ConfigKeyvalueDto;
import com.orbyte.gateway.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheService {


    private final ConfigRepository configRepository;


    @Cacheable(value="config",key="#key", unless = "#result==null")
    public String getConfigFromCache(String key){
        ConfigKeyvalueDto result = configRepository.findByName(key);
        return result != null ? result.getValue() : null;
    }


}
