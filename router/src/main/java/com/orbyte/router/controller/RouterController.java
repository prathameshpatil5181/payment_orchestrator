package com.orbyte.router.controller;

import com.orbyte.router.dto.RouterRequest;
import com.orbyte.router.dto.RouterResponse;
import com.orbyte.router.exception.NullRequestException;
import com.orbyte.router.service.RouterService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("route")
@RequiredArgsConstructor
public class RouterController {
    private final RouterService routerService;

    @PostMapping("/get_processor")
    public ResponseEntity<RouterResponse> routerHandler(@RequestBody RouterRequest routerRequest){

        if(routerRequest == null) throw new NullRequestException("Invalid Request");

        RouterResponse  response = routerService.routingHandling(routerRequest);

        return ResponseEntity.ok(response);

    }

}
