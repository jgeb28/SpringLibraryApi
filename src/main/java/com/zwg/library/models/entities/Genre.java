package com.zwg.library.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Genre {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

}
