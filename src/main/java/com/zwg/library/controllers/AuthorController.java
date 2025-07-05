package com.zwg.library.controllers;

import com.zwg.library.models.dtos.AuthorDto;
import com.zwg.library.models.dtos.CreateAuthorDto;
import com.zwg.library.models.dtos.UpdateAuthorDto;
import com.zwg.library.services.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDto>> getAll() {
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDto> create(@RequestBody @Valid CreateAuthorDto dto) {
        return ResponseEntity.ok(authorService.createAuthor(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorDto> update(@RequestBody @Valid UpdateAuthorDto dto, @PathVariable Long id) {
        return  ResponseEntity.ok(authorService.updateAuthor(dto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
