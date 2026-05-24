package com.orbyte.orchestrator.service.impl;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestService {

    private final RestClient restClient;
    private final RestClient restEurkaClient;

    public RestService(@LoadBalanced RestClient.Builder restEurkaBuilder, RestClient.Builder restClientBuilder){
        this.restClient = restClientBuilder.build();
        this.restEurkaClient =restEurkaBuilder.build();
    }


    public RestClient getRestClient(){
        return restClient;
    }

    public RestClient getRestEurkaClient(){
        return restEurkaClient;
    }


}
