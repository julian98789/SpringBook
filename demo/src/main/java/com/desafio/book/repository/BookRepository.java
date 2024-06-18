package com.desafio.book.repository;

import com.desafio.book.model.BookAuthor;
import com.desafio.book.model.BookData;
import com.desafio.book.model.BookDataJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<BookDataJPA, Long> {

    @Query("SELECT l FROM BookDataJPA l WHERE l.authors LIKE CONCAT('%', (:authors), '%')")
    List<BookDataJPA> searchByAuthorName (String authors);

    List<BookDataJPA> findByLanguagesContains(String language);


}
