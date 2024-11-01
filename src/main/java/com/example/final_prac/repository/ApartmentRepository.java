package com.example.final_prac.repository;

import com.example.final_prac.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    List<Apartment> findByPricePerNightBetween(BigDecimal minPrice, BigDecimal maxPrice);
    List<Apartment> findByBedroomsGreaterThanEqual(int bedrooms);
    List<Apartment> findByAddressContainingIgnoreCase(String address);
}
