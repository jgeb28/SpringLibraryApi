package com.zwg.library.models.dtos;

import java.util.List;

public record AuthorDto (
        long id,
        String firstname,
        String lastname,
        List<BookDto> books
) {}
