package com.orbyte.router.routers.RuleRouter;

import com.orbyte.router.cache.RoutingCacheHandler;
import com.orbyte.router.constants.RouterType;
import com.orbyte.router.dto.RouterRequest;
import com.orbyte.router.dto.RouterResponse;
import com.orbyte.router.exception.NoRoutingRuleFoundException;
import com.orbyte.router.entity.RoutingRules;
import com.orbyte.router.repository.RoutingRulesRepository;
import com.orbyte.router.routers.Router;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleRouter implements Router {


    private final RoutingRulesRepository routingRulesRepository;
    private final RoutingCacheHandler routingCacheHandler;

    @Override
    public RouterType getRouterType() {
        return RouterType.RULE;
    }

    @Override
    public RouterResponse getProcessor(RouterRequest request) {

        log.info("Inside RuleRouter.getProcessor {}", request);

        String cacheKey = buildCacheKey(request);

        List<RoutingRules> eligibleRules = routingRulesRepository.getCandidates(String.valueOf(request.getPaymentType()),request.getAmount(),request.getCurrency(),request.getBinBrand());

        if(!eligibleRules.isEmpty()){
         RoutingRules rule = eligibleRules.getFirst();
         log.info("matched rule {}",rule);
            return RouterResponse.builder().
                    primaryProcessor(rule.getProcessor()).failoverProcessor(rule.getFallbackProcessors()).failoverCodes(null).build();
        }

        else{
            throw new NoRoutingRuleFoundException("Routing does not exits for give transaction");
        }
    }

    private String buildCacheKey(RouterRequest ctx) {
        String bucket = ctx.getAmount().compareTo(new BigInteger("1000"))  < 0 ? "LOW"
                : ctx.getAmount().compareTo(new BigInteger("50000")) < 0 ? "MID"
                  : "HIGH";


        return String.join(":",
                nvl(ctx.getCurrency()),
                nvl(ctx.getBinBrand()),
                nvl(String.valueOf(ctx.getPaymentType())),
                bucket
        );
    }

    private String nvl(String v) { return v != null ? v : "*"; }

}
