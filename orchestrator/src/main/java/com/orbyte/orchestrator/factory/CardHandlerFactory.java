package com.orbyte.orchestrator.factory;

import com.orbyte.constants.PaymentType;
import com.orbyte.constants.Processor;
import com.orbyte.orchestrator.service.Card;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
@Component
public class CardHandlerFactory {
    private final Map<Processor, Card> handlers;

    public CardHandlerFactory(List<Card> handlerList){
        this.handlers = handlerList.stream().collect(Collectors.toMap(
                Card::getProcessor,
                Function.identity()
        ));
    }

    public Card getCardProcessor(Processor processor){
        log.info("inside CardHandlerFactory.getCardProcessor");
        log.info(String.valueOf(processor));
        log.info(this.handlers.toString());
        return Optional.ofNullable(handlers.get(processor)).orElseThrow(()->new IllegalArgumentException("Processor not supported"));
    }
}
