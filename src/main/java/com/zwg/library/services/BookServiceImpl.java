package com.zwg.library.services;

import com.zwg.library.models.dtos.BookDto;
import com.zwg.library.models.dtos.CreateBookDto;
import com.zwg.library.models.dtos.UpdateBookDto;
import com.zwg.library.models.entities.Author;
import com.zwg.library.models.entities.Book;
import com.zwg.library.models.entities.Genre;
import com.zwg.library.repositories.AuthorRepository;
import com.zwg.library.repositories.BookRepository;
import com.zwg.library.repositories.GenreRepository;
import com.zwg.library.utils.BookMapper;
import com.zwg.library.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public List<BookDto> findAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findBookById(long bookId) {
        return BookMapper.toDto(bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + bookId)));
    }

    @Override
    public BookDto createBook(CreateBookDto dto) {
        Author author = authorRepository.findById(dto.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + dto.authorId()));
        Set<Genre> genres = new HashSet<>();
        for(long genreId : dto.genreIds()) {
            genres.add(genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreId )));
        }
        Book newBook = BookMapper.toEntity(dto, author, genres);
        return  BookMapper.toDto(bookRepository.save(newBook));
    }

    @Override
    public BookDto updateBook(UpdateBookDto dto, long bookId) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + bookId));
        Author author = authorRepository.findById(dto.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + dto.authorId()));
        Set<Genre> genres = new HashSet<>();
        for(long genreId : dto.genreIds()) {
            genres.add(genreRepository.findById(genreId)
                    .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreId )));
        }
        existingBook.setTitle(dto.title());
        existingBook.setIsbn(dto.isbn());
        existingBook.setPublicationYear(dto.publicationYear());
        existingBook.setAuthor(author);
        existingBook.setGenres(genres);

        return BookMapper.toDto(bookRepository.save(existingBook));
    }

    @Override
    public void deleteBook(long bookId) {
        if(!bookRepository.existsById(bookId)) {
            throw new ResourceNotFoundException("Book not found: " + bookId );
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<BookDto> findAllBooksByGenre(long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found: " + genreId ));

        return bookRepository.findAll().stream()
                .filter(b -> b.getGenres().contains(genre))
                .map(BookMapper::toDto)
                .collect(Collectors.toList());

    }
}
