package com.desafio.book.master;

import com.desafio.book.model.*;
import com.desafio.book.repository.AuthorRepository;
import com.desafio.book.repository.BookRepository;
import com.desafio.book.service.ConsumeAPI;
import com.desafio.book.service.ConvertData;
import com.desafio.book.service.Methods;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public Master (BookRepository bookRepository , AuthorRepository authorRepository){
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
            opcion = input.nextInt();
            input.nextLine();

            switch (opcion) {
                case 1:
                    searchBookByTitle();
                    break;
            }

        }
        
    }

    private void searchBookByTitle() {
        System.out.println("Ingrese el titulo del libro que desea buscar");
        String nameBook = input.nextLine();
        String url = Base_URL + "?search=" + nameBook.replace(" ","%20");
        System.out.println(url);

        var json = consumeAPI.obtenerDatos(url);
        var convert = convertData.getData(json, Result.class);
        BookData bookData = convert.result().get(0);

        BookDataJPA bookDataJPA = new BookDataJPA(bookData);
        BookAuthorJPA bookAuthorJPA = new BookAuthorJPA().getFirstAuthor(bookData);

        System.out.println("Datos del libro" + bookDataJPA);
        System.out.println("Datos del autor" + bookAuthorJPA);

        Methods methods = new Methods(authorRepository, bookRepository);
        methods.saveBookData(bookAuthorJPA, bookDataJPA);
        System.out.println("Libro guardado correctamente.");
    }

}
