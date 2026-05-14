package com.orbyte.gateway.configuration;

import com.orbyte.gateway.constants.AppContants;
import com.orbyte.gateway.dto.dtoimpl.ConfigKeyvalueDto;
import com.orbyte.gateway.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
@RequiredArgsConstructor
@Slf4j
public class AppConfig implements ApplicationRunner {


    private final RedisTemplate redisTemplate;


    private final ConfigRepository configRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<ConfigKeyvalueDto> config = configRepository.findAllConfigs();

        for(ConfigKeyvalueDto it: config){
           log.debug(it.getName() + " "+it.getValue());
            redisTemplate.opsForValue().setIfAbsent(AppContants.CONFIG_CACHE_PREFIX + it.getName(),it.getValue(),10, TimeUnit.MINUTES);

        }

    }

}
