package com.desafio.book.master;

import com.desafio.book.model.*;
import com.desafio.book.repository.AuthorRepository;
import com.desafio.book.repository.BookRepository;
import com.desafio.book.service.ConsumeAPI;
import com.desafio.book.service.ConvertData;
import com.desafio.book.service.Methods;

import java.util.*;

public class Master {

    private Scanner input = new Scanner(System.in);

    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConvertData convertData = new ConvertData();
    private static final String Base_URL = "https://gutendex.com/books/";
    private List<BookAuthorJPA> author = new ArrayList<>();
    private List<BookDataJPA> book = new ArrayList<>();
    private String language;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    public Master(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void menu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                                        
                    0. Salir
                    """;
            System.out.println(menu);
            try {
                opcion = input.nextInt();
                input.nextLine();

                switch (opcion) {
                    case 1:
                        searchBookByTitle();
                        break;

                    case 2:
                        searchRegisteredBooks();
                        break;
                    case 3:
                        searchRegisteredAuthor();
                        break;
                    case 4:
                        searchForLivingAuthorsInAGivenYear();
                        break;
                    case 5:
                        searchBooksByLanguage();
                        break;
                    case 0:
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero válido.");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                input.nextLine();
            }
        }
    }


    private void searchBookByTitle() {
        System.out.println("Ingrese el titulo del libro que desea buscar");
        String nameBook = input.nextLine();
        String url = Base_URL + "?search=" + nameBook.replace(" ", "%20");
        System.out.println(url);

        var json = consumeAPI.obtenerDatos(url);
        var convert = convertData.getData(json, Result.class);

        if (convert == null || convert.result().isEmpty()) {
            System.out.println("No se encontró ningún libro con el título: " + nameBook);
            return;
        }
        BookData bookData = convert.result().get(0);
        BookDataJPA bookDataJPA = new BookDataJPA(bookData);
        BookAuthorJPA bookAuthorJPA = new BookAuthorJPA().getFirstAuthor(bookData);



        System.out.println("Datos del libro" + bookDataJPA);
        System.out.println("Datos del autor" + bookAuthorJPA);

        Methods methods = new Methods(authorRepository, bookRepository);
        methods.saveBookData(bookAuthorJPA, bookDataJPA);
    }

    private void searchRegisteredBooks() {

        book = bookRepository.findAll();

        book.stream().sorted(Comparator.comparing(BookDataJPA::getAuthors))
                .forEach(System.out::println);
    }

    private void searchRegisteredAuthor() {
        author = authorRepository.findAll();

        author.stream().sorted((Comparator.comparing(BookAuthorJPA::getName)))
                .forEach(System.out::println);
    }

    private void searchForLivingAuthorsInAGivenYear() {
        try {
            System.out.println("Autores vivos desde:");
            int from = input.nextInt();
            System.out.println("Autores vivos hasta:");
            int to = input.nextInt();
            input.nextLine();

            if (from > to) {
                System.out.println("Años inválidos");
            } else {
                author = authorRepository.authorDateBirthDeath(from, to);

                if (author.isEmpty()) {
                    System.out.println("No se encontraron autores vivos en ese rango de años.");
                } else {
                    System.out.println("Autores vivos en el rango de años:");
                    author.stream()
                            .sorted(Comparator.comparing(BookAuthorJPA::getName))
                            .forEach(System.out::println);
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, ingrese un número entero válido.");
            input.nextLine();
        } catch (Exception e) {
            System.out.println("Ocurrió un error al traer los datos: " + e.getMessage());
            input.nextLine();
        }
    }

    private void searchBooksByLanguage() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                INDICA ALGUNA DE LAS OPCIONES
                                        
                1. Español (es)
                2. Inglés (en)
                3. Francés (fr)
                4. Portugues (pt)
                5. Aleman (ny)
                6. Otro (Ingresa un código de idioma válido, ej: Italiano (it))
                                        
                0. Salir
                """;
            System.out.println(menu);

            try {
                opcion = input.nextInt();
                input.nextLine();

                switch (opcion) {
                    case 1:
                        language = "es";
                        break;
                    case 2:
                        language = "en";
                        break;
                    case 3:
                        language = "fr";
                        break;
                    case 4:
                        language = "pt";
                        break;
                    case 5:
                        language = "ny";
                        break;
                    case 6:
                        System.out.print("Ingresa un código de idioma válido, ej: Italiano (it): ");
                        language = input.nextLine();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor, ingrese un número válido.");
                        continue;
                }

                if (opcion != 0) {
                    book = bookRepository.findByLanguagesContains(language);

                    if (book.isEmpty()) {
                        System.out.println("No se hallaron libros para este idioma.\n");
                    } else {
                        System.out.println("Libros encontrados para el idioma '" + language + "':");
                        book.forEach(System.out::println);
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número entero válido.");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
                input.nextLine();
            }
        }
    }

}







