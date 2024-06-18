package com.desafio.book.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "book_data")
public class BookDataJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Column(unique = true)
    private String title;

    @ManyToOne()
    private BookAuthorJPA autor;

    private String authors;
    private String languages;
    private Integer download_count;

    public BookDataJPA () {}

    public BookDataJPA(BookData bookData){
    this.title = bookData.title();
    this.download_count = bookData.download_count();
    this.authors = getFirstAuthor(bookData).getName();
    this.languages = getFirstlanguage(bookData);
    }
    public BookAuthorJPA getFirstAuthor (BookData bookData){
        BookAuthor bookAuthor = bookData.authors().get(0);
        return new BookAuthorJPA (bookAuthor);
    }


    public String getFirstlanguage (BookData bookData){
        String language = bookData.languages().toString();
        return language;
    }


    @Override
    public String toString() {
        return  " *********"+ '\n' +
                "Datos Libro, id:" + ID +", titulo='" + title + '\n' +
                ", descargas=" + download_count + ", autor='" + authors + '\'' +", idioma='" + languages ;

    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookAuthorJPA getAutor() {
        return autor;
    }

    public void setAutor(BookAuthorJPA autor) {
        this.autor = autor;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }
}



