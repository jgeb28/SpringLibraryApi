package com.zwg.library.repositories;

import com.zwg.library.models.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository  extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);
}
