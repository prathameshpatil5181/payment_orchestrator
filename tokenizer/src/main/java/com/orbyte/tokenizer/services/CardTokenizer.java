package com.orbyte.tokenizer.services;

import com.orbyte.constants.Processor;
import com.orbyte.tokenizer.dto.CardInfo;
import com.orbyte.tokenizer.entity.OrbToken;

public interface CardTokenizer {
    public Processor getProcessor();
    public OrbToken createCardToken(CardInfo cardInfo);
}
