package com.orbyte.gateway.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class RestService {

    private final RestClient restClient;

    public RestService(@LoadBalanced RestClient.Builder restClientbuilder) {
        this.restClient = restClientbuilder.build();
    }

    public RestClient.RequestBodySpec postHandler(String url){
        return restClient.post().uri(url);
    }

}
