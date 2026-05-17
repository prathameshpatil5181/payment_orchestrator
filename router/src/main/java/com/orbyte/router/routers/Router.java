package com.orbyte.router.routers;

import com.orbyte.router.constants.RouterType;

public interface Router {
    public RouterType getRouterType();
    public void getProcessor();
}
