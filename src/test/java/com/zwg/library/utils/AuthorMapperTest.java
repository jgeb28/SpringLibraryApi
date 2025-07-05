package com.zwg.library.utils;

import com.zwg.library.models.dtos.AuthorDto;
import com.zwg.library.models.dtos.CreateAuthorDto;
import com.zwg.library.models.entities.Author;
import com.zwg.library.models.entities.Book;
import com.zwg.library.models.entities.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


class AuthorMapperTest {

    @Test
    public void shouldMapAuthorEntityToAuthorDto() {
        // arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Book book = new Book(1L, "title", 1000, "1234123489123", author,
                new HashSet<Genre>(Set.of( new Genre(1L, "Genre"))));
        author.setBooks(List.of(book));
        // act
        AuthorDto dto = AuthorMapper.toDto(author);
        // assert
        Assertions.assertEquals(author.getFirstname(), dto.firstname());
        Assertions.assertEquals(author.getLastname(), dto.lastname());
        Assertions.assertEquals(1, dto.books().size());
        Assertions.assertEquals("title", dto.books().get(0).title());
    }

    @Test
    public void shouldMapCreatedAuthorDtoToAuthorEntity() {
        // arrange
        CreateAuthorDto dto = new CreateAuthorDto("John", "Doe");
        // act
        Author author = AuthorMapper.toEntity(dto);
        // assert
        Assertions.assertEquals(author.getFirstname(), dto.firstname());
        Assertions.assertEquals(author.getLastname(), dto.lastname());
    }
}