package com.zwg.library.utils;

import com.zwg.library.models.entities.Genre;
import com.zwg.library.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreSeeder implements CommandLineRunner {

    private final GenreRepository genreRepository;

    @Override
    public void run(String... args) {
        List<String> genreNames = List.of("Fantasy", "Horror", "Sci-Fi", "Drama");

        for (String name : genreNames) {
            if (!genreRepository.existsByName(name)) {
                Genre genre = new Genre();
                genre.setName(name);
                genreRepository.save(genre);
            }
        }
    }
}
