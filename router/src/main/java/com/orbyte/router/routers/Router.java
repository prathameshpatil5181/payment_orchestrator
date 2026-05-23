package com.orbyte.router.routers;

import com.orbyte.dto.PaymentRequest;
import com.orbyte.router.constants.RouterType;
import com.orbyte.router.dto.RouterRequest;
import com.orbyte.router.dto.RouterResponse;

public interface Router {
    public RouterType getRouterType();
    public RouterResponse getProcessor(RouterRequest request);
}
