package com.example.final_prac.service;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.ApartmentPhoto;

import java.math.BigDecimal;
import java.util.List;

public interface ApartmentService {
    Apartment createApartment(Apartment apartment);
    Apartment updateApartment(Long id, Apartment apartment);
    void deleteApartment(Long id);
    Apartment getApartmentById(Long id);
    List<Apartment> getAllApartments();
    List<Apartment> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<Apartment> findByMinBedrooms(int bedrooms);
    List<Apartment> searchByAddress(String address);
    void addPhotoToApartment(Long apartmentId, ApartmentPhoto photo);
    void removePhotoFromApartment(Long apartmentId, Long photoId);
}

