package com.zwg.library.controllers;

import com.zwg.library.models.dtos.BookDto;
import com.zwg.library.models.dtos.CreateBookDto;
import com.zwg.library.models.dtos.UpdateBookDto;
import com.zwg.library.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    public final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @GetMapping("genre/{genreId}")
    public ResponseEntity<List<BookDto>> getAllByGenreId(@PathVariable Long genreId) {
        return ResponseEntity.ok(bookService.findAllBooksByGenre(genreId));
    }

    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody @Valid CreateBookDto dto) {
        return ResponseEntity.ok(bookService.createBook(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookDto> update(@RequestBody @Valid UpdateBookDto dto, @PathVariable Long id) {
        return  ResponseEntity.ok(bookService.updateBook(dto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
