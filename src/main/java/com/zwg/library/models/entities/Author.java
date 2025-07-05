package com.zwg.library.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;
    @OneToMany(mappedBy = "author")
    private List<Book> books = new LinkedList<>();

}
