package com.orbyte.tokenizer.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BinLookupResponse {

    private NumberInfo number;

    private String scheme;

    private String type;

    private String brand;

    private Country country;

    private Bank bank;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NumberInfo {

        private Integer length;

        private Boolean luhn;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Country {

        private String numeric;

        private String alpha2;

        private String name;

        private String emoji;

        private String currency;

        private Integer latitude;

        private Integer longitude;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bank {

        private String name;
    }
}
