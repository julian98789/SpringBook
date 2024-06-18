package com.desafio.book.repository;



import com.desafio.book.model.BookAuthorJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<BookAuthorJPA, Long> {

    Optional<BookAuthorJPA> findByNameContains(String nameAuthor);

    @Query("SELECT a FROM BookAuthorJPA a WHERE a.birth_year <= :death_year AND (a.death_year >= :birth_year OR a.death_year IS NULL)")
    List<BookAuthorJPA> authorDateBirthDeath(Integer birth_year, Integer death_year);

}

