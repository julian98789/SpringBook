package com.desafio.book.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book_author")
public class BookAuthorJPA {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(unique = true)
    private String name;
    private Integer  birth_year;
    private Integer  death_year;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<BookDataJPA> bookDataJPAList;

    public BookAuthorJPA (){}

    public BookAuthorJPA (BookAuthor bookAuthor){
        this.name = bookAuthor.name();
        this.birth_year = Integer.valueOf(bookAuthor.birth_year());
        this.death_year = Integer.valueOf(bookAuthor.death_year());

    }
    public BookAuthorJPA getFirstAuthor (BookData bookData){
        BookAuthor bookAuthor = bookData.authors().get(0);
        return new BookAuthorJPA (bookAuthor);
    }

    @Override
    public String toString() {
        return
                "Nombre = " + name + "\'" + ", Año de naciemiento =" + birth_year +
                        ", Año de muerte =" + death_year;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public List<BookDataJPA> getBookDataJPAList() {
        return bookDataJPAList;
    }

    public void setBookDataJPAList(List<BookDataJPA> bookDataJPAList) {
        this.bookDataJPAList = bookDataJPAList;
    }
}
