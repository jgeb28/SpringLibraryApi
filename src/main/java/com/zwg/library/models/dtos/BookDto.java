package com.zwg.library.models.dtos;

import com.zwg.library.models.entities.Genre;

import java.util.Set;

public record BookDto(
        long id,
        String title,
        int publicationYear,
        String isbn,
        String authorFirstname,
        String authorLastname,
        Set<String> genreNames
)
{}
