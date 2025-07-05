package com.zwg.library.services;

import com.zwg.library.models.dtos.*;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAllAuthors();
    AuthorDto findAuthorById(long authorId);
    AuthorDto createAuthor(CreateAuthorDto dto);
    AuthorDto updateAuthor(UpdateAuthorDto dto, long authorId);
    void deleteAuthor(long authorId);
}
