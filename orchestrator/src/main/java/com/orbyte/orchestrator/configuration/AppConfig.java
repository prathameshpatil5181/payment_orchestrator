package com.orbyte.orchestrator.configuration;

import com.orbyte.orchestrator.constants.AppContants;
import com.orbyte.orchestrator.dtos.ConfigKeyvalueDto;
import com.orbyte.orchestrator.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
@Slf4j
public class AppConfig implements ApplicationRunner {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ConfigRepository configRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<ConfigKeyvalueDto> config = configRepository.findAllConfigs();

        for(ConfigKeyvalueDto it: config){
            log.debug(it.getName() + " "+it.getValue());
            redisTemplate.opsForValue().setIfAbsent(AppContants.CONFIG_CACHE_PREFIX + it.getName(),it.getValue(),10, TimeUnit.MINUTES);
        }

    }
}
