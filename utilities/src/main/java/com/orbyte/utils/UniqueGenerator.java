package com.orbyte.utils;

import java.util.UUID;

public class UniqueGenerator {


    public String generateUniqueValue(){

        return UUID.randomUUID().toString();

    }

}
