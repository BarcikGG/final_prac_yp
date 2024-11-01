package com.example.final_prac.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.*;

@Data
@Entity
@Table(name = "apartments")
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal pricePerNight;

    @Min(value = 1, message = "Minimum 1 bedroom required")
    private int bedrooms;

    @Min(value = 1, message = "Minimum 1 bathroom required")
    private int bathrooms;

    @NotNull(message = "Area is required")
    @Positive(message = "Area must be positive")
    private Double area;

    private String description;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private List<ApartmentPhoto> photos = new ArrayList<>();

    @OneToMany(mappedBy = "apartment")
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "apartment")
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "apartment_amenities",
            joinColumns = @JoinColumn(name = "apartment_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> amenities = new HashSet<>();
}