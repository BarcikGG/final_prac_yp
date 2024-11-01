package com.example.final_prac.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentDTO {
    private Long id;
    private String title;
    private String description;
    private String address;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal pricePerNight;
    private Double area;
    private List<PhotoDTO> photos;
}