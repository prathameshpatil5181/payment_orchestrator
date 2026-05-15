package com.orbyte.gateway.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {


    @Bean("loadBalancedBuilder")
    @LoadBalanced
    public RestClient.Builder loadBalanced(){
        return RestClient.builder();
    }


    @Bean
    @Primary
    public RestClient.Builder restclientBuilder(){
        return RestClient.builder();
    }

}
