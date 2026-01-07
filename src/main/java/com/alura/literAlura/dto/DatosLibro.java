package com.alura.literAlura.dto;

import java.util.List;

public record DatosLibro(
        String title,
        List<DatosAutor> authors,
        List<String> languages,
        Integer download_count
) {}
