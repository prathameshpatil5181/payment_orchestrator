package com.orbyte.tokenizer.config;

import com.orbyte.tokenizer.constants.TokenizerConstants;
import com.orbyte.tokenizer.dto.ConfigKeyvalueDto;
import com.orbyte.tokenizer.repository.ConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class AppConfig implements ApplicationRunner {

    private final RedisTemplate redisTemplate;

    private final ConfigRepository configRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<ConfigKeyvalueDto> config = configRepository.findAllConfigs();

        for(ConfigKeyvalueDto it: config){
            System.out.println(it.getName() + " "+it.getValue());
            redisTemplate.opsForValue().setIfAbsent(TokenizerConstants.CONFIG_CACHE_PREFIX + it.getName(),it.getValue(),10, TimeUnit.MINUTES);

        }
    }
}
