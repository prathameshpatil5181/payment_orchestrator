package com.orbyte.gateway.dto.carddto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CardErrorResponseDto {
    private String message;
    private String code;
    private String subCode;
    private String txnId;
    private String txnStatus;
    private String txnSubStatus;
}
