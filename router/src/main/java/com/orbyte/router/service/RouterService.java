package com.orbyte.router.service;

import com.orbyte.router.constants.RouterType;
import com.orbyte.router.dto.RouterRequest;
import com.orbyte.router.dto.RouterResponse;
import com.orbyte.router.routers.Router;
import com.orbyte.router.routers.RouterBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouterService {

    private final RouterBuilder routerBuilder;

    public RouterResponse routingHandling(RouterRequest routerRequest){
            Router router = routerBuilder.getRouter(RouterType.RULE);
            RouterResponse response = router.getProcessor(routerRequest);
            return response;
    }
}
