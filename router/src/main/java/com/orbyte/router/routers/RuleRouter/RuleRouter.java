package com.orbyte.router.routers.RuleRouter;

import com.orbyte.router.constants.RouterType;
import com.orbyte.router.routers.Router;
import org.springframework.stereotype.Service;

@Service
public class RuleRouter implements Router {
    @Override
    public RouterType getRouterType() {
        return RouterType.RULE;
    }

    @Override
    public void getProcessor() {

    }
}
