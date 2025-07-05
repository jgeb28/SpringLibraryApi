package com.zwg.library.repositories;

import com.zwg.library.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByGenres_Id(Long genreId);
}
