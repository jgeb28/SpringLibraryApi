package com.zwg.library.utils;

import com.zwg.library.models.dtos.BookDto;
import com.zwg.library.models.dtos.CreateBookDto;
import com.zwg.library.models.entities.Author;
import com.zwg.library.models.entities.Book;
import com.zwg.library.models.entities.Genre;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Set;


class BookMapperTest {

    @Test
    void shouldMapBookEntityToBookDto() {
        //arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Set<Genre> genres = Set.of(new Genre(1L,"Genre"));
        Book book = new Book(1L, "title", 1000, "1234123489123", author, genres);
        //act
        BookDto dto = BookMapper.toDto(book);
        //assert
        Assertions.assertEquals(book.getTitle(), dto.title());
        Assertions.assertEquals(book.getPublicationYear(), dto.publicationYear());
        Assertions.assertEquals(book.getIsbn(), dto.isbn());
        Assertions.assertEquals(book.getAuthor().getFirstname(), dto.authorFirstname());
        Assertions.assertEquals(book.getAuthor().getLastname(), dto.authorLastname());
        Assertions.assertEquals(book.getGenres().iterator().next().getName(), dto.genreNames().iterator().next());
    }

    @Test
    void shouldMapCreatedBookDtoToBookEntity() {
        //arrange
        CreateBookDto dto = new CreateBookDto(
                "title", 1000, "1234123489123", 1, Set.of(1)
        );
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Set<Genre> genres = Set.of( new Genre(1L, "Genre"));
        //act
        Book book = BookMapper.toEntity(dto, author, genres);
        //assert
        Assertions.assertEquals(book.getTitle(), dto.title());
        Assertions.assertEquals(book.getPublicationYear(), dto.publicationYear());
        Assertions.assertEquals(book.getIsbn(), dto.isbn());
        Assertions.assertEquals(book.getAuthor().getFirstname(), author.getFirstname());
        Assertions.assertEquals(book.getAuthor().getLastname(), author.getLastname());
        Assertions.assertEquals(book.getGenres().iterator().next().getName(), genres.iterator().next().getName());
    }
}