package com.zwg.library.utils;

import com.zwg.library.models.dtos.BookDto;
import com.zwg.library.models.dtos.CreateBookDto;
import com.zwg.library.models.entities.Author;
import com.zwg.library.models.entities.Book;
import com.zwg.library.models.entities.Genre;

import java.util.Set;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDto toDto(Book book) {
        return  new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getIsbn(),
                book.getAuthor().getFirstname(),
                book.getAuthor().getLastname(),
                book.getGenres().stream().map(Genre::getName).collect(Collectors.toSet())
        );
    }

    public static Book toEntity(CreateBookDto dto, Author author, Set<Genre> genres) {
        Book book = new Book();
        book.setTitle(dto.title());
        book.setPublicationYear(dto.publicationYear());
        book.setIsbn(dto.isbn());
        book.setAuthor(author);
        book.setGenres(genres);
        return book;
    }
}
