package com.alura.literAlura.principal;

import com.alura.literAlura.dto.RespuestaAPI;
import com.alura.literAlura.repository.AutorRepository;
import com.alura.literAlura.repository.LibroRepository;
import com.alura.literAlura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.alura.literAlura.service.ConsumoAPI;
import com.alura.literAlura.service.ConvierteDatos;


import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    @Autowired
    private LibroRepository libroRepo;
    @Autowired
    private AutorRepository autorRepo;
    @Autowired
    private LibroService servicio;

    private Scanner teclado = new Scanner(System.in);

    @Override
    public void run(String... args) {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
            1 - Buscar libro por título
            2 - Listar libros registrados
            3 - Listar autores registrados
            4 - Listar autores vivos en un año
            5 - Listar libros por idioma
            0 - Salir
            """);

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> libroRepo.findAll().forEach(System.out::println);
                case 3 -> autorRepo.findAll().forEach(System.out::println);
                case 4 -> autoresVivos();
                case 5 -> librosPorIdioma();
            }
        }
    }

    private void buscarLibro() {
        System.out.print("Título: ");
        String titulo = teclado.nextLine();

        String json = new ConsumoAPI()
                .obtenerDatos("https://gutendex.com/books/?search=" + titulo);

        RespuestaAPI respuesta = new ConvierteDatos()
                .convertir(json, RespuestaAPI.class);

        respuesta.results().forEach(servicio::guardarLibro);
    }

    private void autoresVivos() {
        System.out.print("Año: ");
        int anio = teclado.nextInt();
        autorRepo.autoresVivosEnAnio(anio).forEach(System.out::println);
    }

    private void librosPorIdioma() {
        System.out.print("Idioma (es/en/fr): ");
        String idioma = teclado.nextLine();
        libroRepo.findByIdioma(idioma).forEach(System.out::println);
    }
}
