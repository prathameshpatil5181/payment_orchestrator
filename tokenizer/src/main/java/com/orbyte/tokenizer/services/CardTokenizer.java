package com.orbyte.tokenizer.services;

import com.orbyte.tokenizer.dto.CardInfo;
import com.orbyte.tokenizer.entity.OrbToken;

public interface CardTokenizer {
    public OrbToken createCardToken(CardInfo cardInfo);
}
