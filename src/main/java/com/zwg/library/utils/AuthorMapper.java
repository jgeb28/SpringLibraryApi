package com.zwg.library.utils;

import com.zwg.library.models.dtos.AuthorDto;
import com.zwg.library.models.dtos.CreateAuthorDto;
import com.zwg.library.models.entities.Author;

import java.util.LinkedList;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getFirstname(),
                author.getLastname(),
                author.getBooks()
                        .stream()
                        .map(BookMapper::toDto)
                        .collect(Collectors.toList())
        );
    }

    public static Author toEntity(CreateAuthorDto dto) {
        Author author = new Author();
        author.setFirstname(dto.firstname());
        author.setLastname(dto.lastname());
        author.setBooks(new LinkedList<>());

        return  author;
    }
}
