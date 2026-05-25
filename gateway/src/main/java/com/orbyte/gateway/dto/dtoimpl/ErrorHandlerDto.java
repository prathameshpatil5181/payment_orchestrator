package com.orbyte.gateway.dto.dtoimpl;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorHandlerDto {
    private String status;
    private String subStatus;
    private String message;
}
