package com.orbyte.orchetrator.configuration;

import com.orbyte.orchetrator.cache.RedisConfig;
import com.orbyte.orchetrator.constants.AppContants;
import com.orbyte.orchetrator.dtos.ConfigKeyvalueDto;
import com.orbyte.orchetrator.entity.Config;
import com.orbyte.orchetrator.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
public class AppConfig implements ApplicationRunner {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ConfigRepository configRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<ConfigKeyvalueDto> config = configRepository.findAllConfigs();

        for(ConfigKeyvalueDto it: config){
            System.out.println(it.getName() + " "+it.getValue());
            redisTemplate.opsForValue().setIfAbsent(AppContants.CONFIG_CACHE_PREFIX + it.getName(),it.getValue(),10, TimeUnit.MINUTES);

        }

    }
}
