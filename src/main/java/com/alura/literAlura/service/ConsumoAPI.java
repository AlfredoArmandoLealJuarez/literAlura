package com.alura.literAlura.service;

import org.springframework.web.client.RestTemplate;

public class ConsumoAPI {

    public String obtenerDatos(String url) {
        return new RestTemplate().getForObject(url, String.class);
    }
}
