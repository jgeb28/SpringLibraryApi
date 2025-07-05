package com.zwg.library.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record UpdateAuthorDto (
        @NotBlank String firstname,
        @NotBlank String lastname
) {}
