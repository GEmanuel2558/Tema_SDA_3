package com.tema.sda.Tema_SDA_3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonCastHelper {

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw e;
        }
    }

}