package com.aluracursos.literalura.main;

import com.aluracursos.literalura.api.ConsumoAPI;
import com.aluracursos.literalura.api.ConvierteDatos;
import com.aluracursos.literalura.datosBusqueda.DatosAutor;
import com.aluracursos.literalura.datosBusqueda.DatosLibro;
import com.aluracursos.literalura.model.ListsOfBooks;
import com.aluracursos.literalura.repository.AutoresRepository;
import com.aluracursos.literalura.repository.LibrosRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    private LibrosRepository librosRepository;
    private AutoresRepository autoresRepository;

    public Main(LibrosRepository librosRepository, AutoresRepository autoresRepository) {
        this.librosRepository = librosRepository;
        this.autoresRepository = autoresRepository;
    }


    public void mostrarMenu(){
        var opcion= -1;
        while (opcion !=0){
            var menu = """
                    ---------------------------------------------------------------------
                    Seleccione la opcion escribiendo el numero correspondiente en el menu
                    
                    1- Buscar libro por titulo
                    2- Listar libros registrados
                    3- Listar Autores registrados
                    4- Listar Autores vivos en un determinado año
                    5- Listar libros por idioma
                    0- Salir
                    ---------------------------------------------------------------------                    
                    """;
            System.out.println(menu);
            opcion=teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    ListarLibrosRegistrados();
                    break;
                case 3:
                    ListarAutoresRegistrados();
                    break;
                case 4:
                    ListarAutoresVivosEnUnDeterminado();
                    break;
                case 5:
                    ListarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
        }
    }

}

    private void buscarLibroPorTitulo() {
        // Solicitar al usuario el título del libro
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el título del libro que desea buscar: ");
        String title = scanner.nextLine();

        // Buscar si ya existe un libro con el mismo título en la base de datos
        Optional<DatosLibro> libroExistente = librosRepository.findByTitle(title);

        if (libroExistente.isPresent()) {
            // Si el libro ya existe, imprimir mensaje
            System.out.println("El libro ya se encuentra en la base de datos: " + title);
        } else {
            // Obtener los datos desde la API y proceder a guardar si no existe
            ListsOfBooks datos = getDatos(title);
            System.out.println("En hora buena! Un libro nuevo para la base de datos.");

            if (!datos.results().isEmpty()) {
                DatosLibro libroNuevo = new DatosLibro(datos.results().get(0));
                libroNuevo.setTitle(title); // Establecer el título sin modificarlo
                librosRepository.save(libroNuevo);
                System.out.println("Libro guardado exitosamente: " + title);
            } else {
                System.out.println("No se encontraron resultados en los datos para el título: " + title);
            }
        }
    }

    private ListsOfBooks getDatos(String title) {
        // Reemplazar espacios por %20 solo para la URL, sin modificar el título original
        String formattedTitle = title.replace(" ", "%20");
        System.out.println("Buscando información para el título: " + title);
        String url = URL_BASE + formattedTitle;
        //System.out.println("URL de consulta: " + url);

        String json = consumoAPI.obtenerDatos(url);
        //System.out.println("Respuesta JSON: " + json);

        return conversor.obtenerDatos(json, ListsOfBooks.class);
    }



    private void ListarLibrosRegistrados() {
        List<DatosLibro> libros = librosRepository.findAll();

        if (!libros.isEmpty()) {

            for (DatosLibro libro : libros) {
                System.out.println("---------------------------------------------------------------------\n");
                System.out.println(" Titulo: " + libro.getTitle());
                System.out.println(" Autor: " + libro.getAutor().getName());
                System.out.println(" Idioma: " + libro.getLanguages());
                System.out.println(" Descargas: " + libro.getDownload_count());
                System.out.println("\n---------------------------------------------------------------------\n");
            }

        } else {
            System.out.println("\n\n ----- NO SE ENCONTRARON RESULTADOS ---- \n\n");
        }
    }

    private void ListarAutoresRegistrados() {
        List<DatosAutor> autores = autoresRepository.findAll();

        if (!autores.isEmpty()) {
            for (DatosAutor autor : autores) {
                System.out.println("\n\n----------------------------AUTOR ----------------------------\n");
                System.out.println(" Nombre: " + autor.getName());
                System.out.println(" Fecha de Nacimiento: " + autor.getBirth_year());
                System.out.println(" Fecha de Fallecimiento: " + autor.getDeath_year());
                System.out.println(" Libros: " + autor.getLibros().getTitle());
                System.out.println("\n---------------------------------------------------------------------\n");
            }
        } else {
            System.out.println("\n\n ----- NO HAY ENCONTRARON RESULTADOS PARA LA BUSQUEDA---- \n\n");

        }
    }

    private void ListarAutoresVivosEnUnDeterminado() {
        System.out.println("Escriba el año en el que vivio: ");
        var anio = teclado.nextInt();
        teclado.nextLine();

        List<DatosAutor> autores = autoresRepository.findForYear(anio);

        if (!autores.isEmpty()) {
            for (DatosAutor autor : autores) {
                System.out.println("\n\n--------------------------- Autores Vivos -------------------------\n");
                System.out.println(" Nombre: " + autor.getName());
                System.out.println(" Fecha de nacimiento: " + autor.getBirth_year());
                System.out.println(" Fecha de fallecimiento: " + autor.getDeath_year());
                System.out.println(" Libros: " + autor.getLibros().getTitle());
                System.out.println("\n---------------------------------------------------------------------\n");
            }
        } else {
            System.out.println("\n\n ----- NO HAY ENCONTRARON RESULTADOS PARA LA BUSQUEDA ---- \n\n");

        }
    }


    private void ListarLibrosPorIdioma() {
        var menu = """
				Seleccione un Idioma:
					1.- Español
					2.- Ingles
				
					""";
        System.out.println(menu);
        var idioma = teclado.nextInt();
        teclado.nextLine();

        String seleccion = "";

        if(idioma == 1) {
            seleccion = "es";
        } else 	if(idioma == 2) {
            seleccion = "en";
        }else 	if(idioma == 3) {
            seleccion = "fr";
        }
        System.out.println(seleccion);
        List<DatosLibro> libros = librosRepository.findForLanguages(seleccion);

        if (!libros.isEmpty()) {

            for (DatosLibro libro : libros) {
                System.out.println("\n\n----------------------------- LIBROS POR IDIOMA--------------------------\n");
                System.out.println(" Titulo: " + libro.getTitle());
                System.out.println(" Autor: " + libro.getAutor().getName());
                System.out.println(" Idioma: " + libro.getLanguages());
                System.out.println(" Descargas: " + libro.getDownload_count());
                System.out.println("\n---------------------------------------------------------------\n\n");
            }

        } else {
            System.out.println("\n\n ----- NO HAY ENCONTRARON RESULTADOS PARA LA BUSQUEDA ---- \n\n");
        }
    }


}


