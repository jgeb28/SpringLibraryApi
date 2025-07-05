package com.zwg.library.services;

import com.zwg.library.models.dtos.BookDto;
import com.zwg.library.models.dtos.CreateBookDto;
import com.zwg.library.models.dtos.UpdateBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAllBooks();
    BookDto findBookById(long bookId);
    BookDto createBook(CreateBookDto dto);
    BookDto updateBook(UpdateBookDto dto, long bookId);
    void deleteBook(long bookId);
    List<BookDto> findAllBooksByGenre(long genreId);
}
