package com.orbyte.router.routers;

import com.orbyte.router.constants.RouterType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RouterBuilder {

    private final Map<RouterType,Router> routers;

    public RouterBuilder (List<Router> availableRouters){
       this.routers =  availableRouters.stream().collect(Collectors.toMap(
               Router::getRouterType,
               Function.identity()
       ));
    }

    public Router getRouter(RouterType routerType){
        log.info("inside RouterBuilder.getRouter");
        log.info(String.valueOf(routerType));
        return Optional.ofNullable(routers.get(routerType)).orElseThrow(()->new IllegalArgumentException("Router type not supported"));
    }

}
