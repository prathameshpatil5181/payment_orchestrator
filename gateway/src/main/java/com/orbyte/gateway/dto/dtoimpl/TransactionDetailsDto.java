package com.orbyte.gateway.dto.dtoimpl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDetailsDto {
    int amount;
    String currency;
    String token;
    // add the other details such as address and other required details
}

