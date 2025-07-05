package com.zwg.library.services;

import com.zwg.library.models.dtos.AuthorDto;
import com.zwg.library.models.dtos.CreateAuthorDto;
import com.zwg.library.models.dtos.UpdateAuthorDto;
import com.zwg.library.models.entities.Author;
import com.zwg.library.repositories.AuthorRepository;
import com.zwg.library.utils.AuthorMapper;
import com.zwg.library.utils.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDto> findAllAuthors() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto findAuthorById(long authorId) {
        return AuthorMapper.toDto(authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found - id: " + authorId)));
    }

    @Override
    public AuthorDto createAuthor(CreateAuthorDto dto) {
        Author author = AuthorMapper.toEntity(dto);
        return AuthorMapper.toDto(authorRepository.save(author));
    }

    @Override
    public AuthorDto updateAuthor(UpdateAuthorDto dto, long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found - id: " + authorId));
        author.setFirstname(dto.firstname());
        author.setLastname(dto.lastname());

        return AuthorMapper.toDto(authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(long authorId) {
        if(!authorRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Author not found - id: " + authorId);
        }
        authorRepository.deleteById(authorId);
    }
}
