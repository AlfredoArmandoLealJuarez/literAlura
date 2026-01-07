package com.alura.literAlura.service;

import com.alura.literAlura.dto.DatosAutor;
import com.alura.literAlura.dto.DatosLibro;
import com.alura.literAlura.model.Autor;
import com.alura.literAlura.model.Libro;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepo;

    @Autowired
    private AutorRepository autorRepo;

    public void guardarLibro(DatosLibro datos) {
        if (libroRepo.findByTituloContainingIgnoreCase(datos.title()).isPresent()) {
            return;
        }

        DatosAutor autorDatos = datos.authors().get(0);

        Autor autor = autorRepo.findAll().stream()
                .filter(a -> a.getNombre().equalsIgnoreCase(autorDatos.name()))
                .findFirst()
                .orElseGet(() -> {
                    Autor nuevo = new Autor();
                    nuevo.setNombre(autorDatos.name());
                    nuevo.setAnioNacimiento(autorDatos.birth_year());
                    nuevo.setAnioFallecimiento(autorDatos.death_year());
                    return autorRepo.save(nuevo);
                });

        Libro libro = new Libro();
        libro.setTitulo(datos.title());
        libro.setIdioma(datos.languages().get(0));
        libro.setDescargas(datos.download_count());
        libro.setAutor(autor);

        libroRepo.save(libro);
    }
}
