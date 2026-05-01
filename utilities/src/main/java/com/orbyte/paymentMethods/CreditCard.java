package com.orbyte.paymentMethods;


import lombok.Data;

@Data
public class CreditCard {


        private String cardNumber;
        private String cardHolderName;
        private String expiryDate;
        private String cvv;



}
