package com.desafio.book.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        String title,
        List<BookAuthor> authors,
        List<String> languages,
        Integer download_count
) {
}