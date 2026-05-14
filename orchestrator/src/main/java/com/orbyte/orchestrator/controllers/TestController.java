package com.orbyte.orchestrator.controllers;


import com.orbyte.orchestrator.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    CacheService cacheService;

    @GetMapping("/cache")
    public String cacheTesting(@RequestParam String key){
        return cacheService.getConfigFromCache(key);
    }
    

}
