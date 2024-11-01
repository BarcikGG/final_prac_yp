package com.example.final_prac.service;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.ApartmentPhoto;
import com.example.final_prac.repository.ApartmentPhotoRepository;
import com.example.final_prac.repository.ApartmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentPhotoRepository photoRepository;

    @Override
    public Apartment createApartment(Apartment apartment) {
        return apartmentRepository.save(apartment);
    }

    @Override
    public Apartment updateApartment(Long id, Apartment apartmentDetails) {
        Apartment apartment = getApartmentById(id);

        apartment.setTitle(apartmentDetails.getTitle());
        apartment.setAddress(apartmentDetails.getAddress());
        apartment.setPricePerNight(apartmentDetails.getPricePerNight());
        apartment.setBedrooms(apartmentDetails.getBedrooms());
        apartment.setBathrooms(apartmentDetails.getBathrooms());
        apartment.setArea(apartmentDetails.getArea());
        apartment.setDescription(apartmentDetails.getDescription());
        apartment.setAmenities(apartmentDetails.getAmenities());

        return apartmentRepository.save(apartment);
    }

    @Override
    public void deleteApartment(Long id) {
        apartmentRepository.deleteById(id);
    }

    @Override
    public Apartment getApartmentById(Long id) {
        return apartmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
    }

    @Override
    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    @Override
    public List<Apartment> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return apartmentRepository.findByPricePerNightBetween(minPrice, maxPrice);
    }

    @Override
    public List<Apartment> findByMinBedrooms(int bedrooms) {
        return apartmentRepository.findByBedroomsGreaterThanEqual(bedrooms);
    }

    @Override
    public List<Apartment> searchByAddress(String address) {
        return apartmentRepository.findByAddressContainingIgnoreCase(address);
    }

    @Override
    public void addPhotoToApartment(Long apartmentId, ApartmentPhoto photo) {
        Apartment apartment = getApartmentById(apartmentId);
        photo.setApartment(apartment);
        photoRepository.save(photo);
    }

    @Override
    public void removePhotoFromApartment(Long apartmentId, Long photoId) {
        Apartment apartment = getApartmentById(apartmentId);
        photoRepository.deleteById(photoId);
    }
}
