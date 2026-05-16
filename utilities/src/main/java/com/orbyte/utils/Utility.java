package com.orbyte.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility{
    public static JsonNode parseJson(String json) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(json);

        } catch (JsonProcessingException ex) {

            throw new RuntimeException(
                    "Invalid JSON",
                    ex
            );
        }
    }
}
