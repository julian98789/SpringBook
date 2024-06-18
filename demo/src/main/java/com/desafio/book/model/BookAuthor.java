package com.desafio.book.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookAuthor(
        String name,
        Integer birth_year,
        Integer death_year

) {
}
