package com.zwg.library.repositories;

import com.zwg.library.models.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {}
