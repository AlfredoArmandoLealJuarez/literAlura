package com.alura.literAlura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos {

    private ObjectMapper mapper = new ObjectMapper();

    public <T> T convertir(String json, Class<T> clase) {
        try {
            return mapper.readValue(json, clase);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
