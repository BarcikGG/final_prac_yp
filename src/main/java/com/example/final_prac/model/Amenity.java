package com.example.final_prac.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "amenities")
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(unique = true)
    private String name;

    private String icon;

    @ManyToMany(mappedBy = "amenities")
    private Set<Apartment> apartments;
}
