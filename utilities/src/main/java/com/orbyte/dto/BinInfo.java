package com.orbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BinInfo {
    /**
     * "binDetails": {
     *     "number": {
     *       "length": null,
     *       "luhn": null
     *     },
     *     "scheme": "visa",
     *     "type": "credit",
     *     "brand": "Visa Classic",
     *     "country": {
     *       "numeric": "826",
     *       "alpha2": "GB",
     *       "name": "United Kingdom of Great Britain and Northern Ireland (the)",
     *       "emoji": "🇬🇧",
     *       "currency": "GBP",
     *       "latitude": 54,
     *       "longitude": -2
     *     },
     *     "bank": {
     *       "name": "Stripe Payments Uk Limited"
     *     }
     */

    private String scheme;
    private String cardType;
    private String brand;
    private String country;
    private String currency;
    private String bank;
}
