package com.zwg.library.services;

import com.zwg.library.models.dtos.AuthorDto;
import com.zwg.library.models.dtos.CreateAuthorDto;
import com.zwg.library.models.dtos.UpdateAuthorDto;
import com.zwg.library.models.entities.Author;
import com.zwg.library.repositories.AuthorRepository;
import com.zwg.library.utils.AuthorMapper;
import com.zwg.library.utils.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorService = new AuthorServiceImpl(authorRepository);
    }


    @Test
    void ShouldSuccessfullyFindAllAuthors() {
        // arrange
        Author author1 = new Author(1L, "John", "Doe", new LinkedList<>());
        Author author2 = new Author(2L, "John2", "Doe2", new LinkedList<>());
        Mockito.when(authorRepository.findAll()).thenReturn(List.of(author1,author2));
        // act
        List<AuthorDto> result = authorService.findAllAuthors();
        // assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("John", result.get(0).firstname());
        Assertions.assertEquals("John2", result.get(1).firstname());
    }

    @Test
    void ShouldSuccessfullyFindAuthorById() {
        // arrange
        Author author1 = new Author(1L, "John", "Doe", new LinkedList<>());
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        // act
        AuthorDto result = authorService.findAuthorById(1L);
        // assert
        Assertions.assertEquals(result.firstname(), author1.getFirstname());
    }

    @Test
    void ShouldSuccessfullyCreateNewAuthor() {
        // arrange
        CreateAuthorDto authorDto = new CreateAuthorDto("John", "Doe");
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Mockito.when(authorRepository.save(Mockito.any(Author.class))).thenReturn(author);
        // act
        AuthorDto result = authorService.createAuthor(authorDto);
        // assert
        Assertions.assertEquals(result.firstname(), authorDto.firstname());
        Assertions.assertEquals(result.lastname(), authorDto.lastname());
    }

    @Test
    void ShouldSuccessfullyUpdateExistingAuthorData() {
        // arrange
        UpdateAuthorDto authorDto = new UpdateAuthorDto("John2", "Doe2");
        Author author = new Author(1L, "John", "Doe", new LinkedList<>());
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        // act
        AuthorDto result = authorService.updateAuthor(authorDto,1L);
        // assert
        Assertions.assertEquals(result.firstname(), authorDto.firstname());
        Assertions.assertEquals(result.lastname(), authorDto.lastname());
    }

    @Test
    void ShouldSuccessfullyDeleteExistingAuthor() {
        // arrange
        Mockito.when(authorRepository.existsById(1L)).thenReturn(true);
        Mockito.doNothing().when(authorRepository).deleteById(1L);
        // act
        authorService.deleteAuthor(1L);
        // assert
        Mockito.verify(authorRepository).deleteById(1L);
    }

    @Test
    void  ShouldThrowWhenDeletingNotExistingAuthor() {
        // arrange
        Mockito.when(authorRepository.existsById(1L)).thenReturn(false);
        // act & assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            authorService.deleteAuthor(1L);
        });
    }

    @Test
    void  ShouldThrowWhenFindingNotExistingAuthorById() {
        // arrange
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        // act & assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            authorService.findAuthorById(1L);
        });
    }

    @Test
    void  ShouldThrowWhenUpdatingNotExistingAuthor() {
        // arrange
        UpdateAuthorDto authorDto = new UpdateAuthorDto("John2", "Doe2");
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());
        // act & assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            authorService.updateAuthor(authorDto,1L);
        });
    }


}