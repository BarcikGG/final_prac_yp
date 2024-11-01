package com.example.final_prac.repository;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.ApartmentPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentPhotoRepository extends JpaRepository<ApartmentPhoto, Long> {
    List<ApartmentPhoto> findByApartment(Apartment apartment);
}
