package com.example.final_prac.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "apartment_photos")
public class ApartmentPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String photoUrl;

    private String description;

    @ManyToOne
    @JoinColumn(name = "apartment_id", nullable = false)
    private Apartment apartment;
}
