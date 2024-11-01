package com.example.final_prac.repository;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.Booking;
import com.example.final_prac.model.BookingStatus;
import com.example.final_prac.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long user_id);
    List<Booking> findByApartment(Apartment apartment);
    List<Booking> findByStatus(BookingStatus status);
    Long countByStatus(BookingStatus status);
    List<Booking> findByApartmentAndCheckInDateGreaterThanEqualAndCheckOutDateLessThanEqual(
            Apartment apartment, LocalDate startDate, LocalDate endDate);
}
