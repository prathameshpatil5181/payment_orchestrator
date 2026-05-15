package com.orbyte.gateway.dto.dtoimpl;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentInfoDTO {
    @NonNull
    private String currency;
    @NonNull
    private BigInteger amount;
    @NonNull
    private LocalDateTime transactionDate;
}
