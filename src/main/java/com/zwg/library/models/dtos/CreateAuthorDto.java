package com.zwg.library.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateAuthorDto (
        @NotBlank String firstname,
        @NotBlank String lastname
){}
