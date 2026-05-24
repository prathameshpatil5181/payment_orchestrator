package com.orbyte.tokenizer.services;

import com.orbyte.constants.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CardTokenizerFactory {
    private Map<Processor, CardTokenizer> tokenizers;

    public CardTokenizerFactory(List<CardTokenizer> cardTokenizers){
       this.tokenizers =  cardTokenizers.stream().collect(Collectors.toMap(
              CardTokenizer::getProcessor,
                Function.identity()
       ));
    }

    public CardTokenizer getCardTokenizer(Processor processor){
        log.info("inside CardHandlerFactory.getCardProcessor");
        log.info(String.valueOf(processor));
        log.info(this.tokenizers.toString());
        return Optional.ofNullable(tokenizers.get(processor)).orElseThrow(()->new IllegalArgumentException("Processor not supported"));
    }

}
