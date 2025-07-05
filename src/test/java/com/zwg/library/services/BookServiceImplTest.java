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
import com.zwg.library.utils.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private GenreRepository genreRepository;


    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookServiceImpl(bookRepository, authorRepository, genreRepository);
    }

    @Test
    void ShouldSuccessfullyFindAllBooks() {
        // arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Set<Genre> genres = Set.of(new Genre(1L,"Genre"));
        Book book1 = new Book(1L, "title", 1000, "1231321321231",author, genres );
        Book book2 = new Book(2L, "title2", 1001, "1231321321232",author, genres );
        Mockito.when(bookRepository.findAll()).thenReturn(List.of(book1,book2));
        // act
        List<BookDto> result = bookService.findAllBooks();
        // assert
        assertBookDtoMatchesBook(result.get(0), book1);
        assertBookDtoMatchesBook(result.get(1), book2);
    }

    @Test
    void ShouldSuccessfullyFindBookById() {
        // arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Set<Genre> genres = Set.of(new Genre(1L,"Genre"));
        Book book1 = new Book(1L, "title", 1000, "1231321321231",author, genres );
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        // act
        BookDto result = bookService.findBookById(1L);
        // assert
        assertBookDtoMatchesBook(result, book1);
    }

    @Test
    void ShouldSuccessfullyCreateNewBook() {
        // arrange
        CreateBookDto bookDto = new CreateBookDto("title", 1000, "1231321321231", 1, Set.of(1));
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Genre genre = new Genre(1L,"Genre");
        Book book1 = new Book(1L,"title", 1000, "1231321321231", author, Set.of(genre));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenReturn(book1);
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Mockito.when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        // act
        BookDto result = bookService.createBook(bookDto);
        // assert
        assertEquals(bookDto.title(), result.title());
        assertEquals(bookDto.publicationYear(), result.publicationYear());
        assertEquals(bookDto.isbn(), result.isbn());
        assertEquals(author.getFirstname(), result.authorFirstname());
        assertEquals(genre.getName(),
                result.genreNames().iterator().next());
    }

    @Test
    void ShouldSuccessfullyUpdateExistingBook() {
        // arrange
        UpdateBookDto bookDto = new UpdateBookDto("title2", 1001, "1231321321232", 1, Set.of(1));
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Genre genre = new Genre(1L,"Genre");
        Book book1 = new Book(1L,"title", 1000, "1231321321231", author, Set.of(genre));
        Book book2 = new Book(1L,"title2", 1001, "1231321321232", author, Set.of(genre));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        Mockito.when(bookRepository.save(book2)).thenReturn(book2);
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Mockito.when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        // act
        BookDto result = bookService.updateBook(bookDto, 1L);
        // assert
        assertEquals(bookDto.title(), result.title());
        assertEquals(bookDto.publicationYear(), result.publicationYear());
        assertEquals(bookDto.isbn(), result.isbn());
        assertEquals(author.getFirstname(), result.authorFirstname());
        assertEquals(genre.getName(),
                result.genreNames().iterator().next());
    }

    @Test
    void ShouldSuccessfullyDeleteExistingBook() {
        // arrange
        Mockito.when(bookRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(bookRepository).deleteById(1L);
        // act
        bookService.deleteBook(1L);
        // assert
        Mockito.verify(bookRepository).deleteById(1L);
    }

    @Test
    void ShouldSuccessfullyFindAllBooksByGenre() {
        // arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Genre genre = new Genre(1L,"Genre");
        Genre otherGenre = new Genre(2L,"OtherGenre");
        Book book1 = new Book(1L, "title", 1000, "1231321321231",author, Set.of(genre) );
        Book book2 = new Book(2L, "title2", 1001, "1231321321232",author, Set.of(genre) );
        Book book3 = new Book(3L, "title3", 1002, "1231321321232",author, Set.of(otherGenre) );
        Mockito.when(bookRepository.findAllByGenres_Id(1L)).thenReturn(List.of(book1,book2));
        Mockito.when(genreRepository.existsById(1L)).thenReturn(true);
        Mockito.when(genreRepository.existsById(2L)).thenReturn(true);
        // act
        List<BookDto> result = bookService.findAllBooksByGenre(1L);
        // assert
        assertEquals(2, result.size());
        assertBookDtoMatchesBook(result.get(0), book1);
        assertBookDtoMatchesBook(result.get(1), book2);
        assertNotEquals(book1.getGenres().iterator().next().getName(),
                otherGenre.getName());
        assertNotEquals(book2.getGenres().iterator().next().getName(),
                otherGenre.getName());
    }

    @Test
    void  ShouldThrowWhenDeletingNotExistingBook() {
        // arrange
        Mockito.when(bookRepository.existsById(1L)).thenReturn(false);
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.deleteBook(1L);
        });
    }

    @Test
    void  ShouldThrowWhenFindingNotExistingBookById() {
        // arrange
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.findBookById(1L);
        });
    }

    @Test
    void  ShouldThrowWhenUpdatingNotExistingBook() {
        // arrange
        UpdateBookDto bookDto = new UpdateBookDto("title2", 1001, "1231321321232", 1, Set.of(1));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.updateBook(bookDto, 1L);
        });
    }

    @Test
    void  ShouldThrowWhenUpdatingExistingBookWithNotExistingAuthor() {
        // arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Genre genre = new Genre(1L,"Genre");
        Book book1 = new Book(1L,"title", 1000, "1231321321231", author, Set.of(genre));
        UpdateBookDto bookDto = new UpdateBookDto("title2", 1001, "1231321321232", 1, Set.of(1));
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.updateBook(bookDto, 1L);
        });
    }

    @Test
    void  ShouldThrowWhenUpdatingExistingBookWithNotExistingGenre() {
        // arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Genre genre = new Genre(1L,"Genre");
        Book book1 = new Book(1L,"title", 1000, "1231321321231", author, Set.of(genre));
        UpdateBookDto bookDto = new UpdateBookDto("title2", 1001, "1231321321232", 1, Set.of(1));
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        Mockito.when(genreRepository.findById(1L)).thenReturn(Optional.empty());
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.updateBook(bookDto, 1L);
        });
    }

    @Test
    void  ShouldThrowWhenCreatingNewBookWithNotExistingAuthor() {
        // arrange
        CreateBookDto bookDto = new CreateBookDto("title", 1000, "1231321321231", 1, Set.of(1));
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.createBook(bookDto);
        });
    }

    @Test
    void  ShouldThrowWhenCreatingNewBookWithNotExistingGenre() {
        // arrange
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        CreateBookDto bookDto = new CreateBookDto("title", 1000, "1231321321231", 1, Set.of(1));
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Mockito.when(genreRepository.findById(1L)).thenReturn(Optional.empty());
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.createBook(bookDto);
        });
    }

    @Test
    void ShouldThrowWhenFindingAllBooksByNotExistingGenre() {
        // arrange
        Mockito.when(genreRepository.existsById(1L)).thenReturn(false);
        // act & assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.findAllBooksByGenre(1L);
        });

    }

    private void assertBookDtoMatchesBook(BookDto dto, Book book) {
        assertEquals(book.getTitle(), dto.title());
        assertEquals(book.getPublicationYear(), dto.publicationYear());
        assertEquals(book.getIsbn(), dto.isbn());
        assertEquals(book.getAuthor().getFirstname(), dto.authorFirstname());
        assertEquals(
                book.getGenres().stream().map(Genre::getName).sorted().toList(),
                dto.genreNames().stream().sorted().toList()
        );
    }



}