package com.zwg.library.models.dtos;

import jakarta.validation.constraints.*;

import java.util.Set;

public record CreateBookDto (
        @NotBlank String title,
        @Min(1) @Max(2025) int publicationYear,
        @NotBlank @Pattern(regexp = "\\d{13}")String isbn,
        @NotNull long authorId,
        @NotEmpty Set<Integer> genreIds
){
}
