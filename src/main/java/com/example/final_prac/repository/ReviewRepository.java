package com.example.final_prac.repository;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.Review;
import com.example.final_prac.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByApartment(Apartment apartment);
    List<Review> findByUser(User user);
    List<Review> findByApartmentId(Long apartmentId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.apartment = :apartment")
    Double findAverageRatingByApartment(@Param("apartment") Apartment apartment);
}
