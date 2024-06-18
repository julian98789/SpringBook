package com.desafio.book.service;

import com.desafio.book.model.BookAuthorJPA;
import com.desafio.book.model.BookDataJPA;
import com.desafio.book.repository.AuthorRepository;
import com.desafio.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Methods {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public Methods(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void saveBookData(BookAuthorJPA bookAuthorJPA, BookDataJPA bookDataJPA) {
        Optional<BookAuthorJPA> findAuthor = authorRepository.findByNameContains(bookAuthorJPA.getName());

        if (findAuthor.isPresent()){
            System.out.println("El autor ya está guardado");
        } else {
            try {
                authorRepository.save(bookAuthorJPA);
                bookDataJPA.setAutor(bookAuthorJPA);
            } catch (Exception e) {
                System.out.println("Ocurrió un error al guardar el autor: " + e.getMessage());
            }
        }

        try {
            bookRepository.save(bookDataJPA);
            System.out.println("Libro guardado correctamente.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al guardar el libro: " + e.getMessage());
        }
    }
}
