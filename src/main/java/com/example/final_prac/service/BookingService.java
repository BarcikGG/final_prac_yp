package com.example.final_prac.service;

import com.example.final_prac.model.Booking;
import com.example.final_prac.model.BookingStatus;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking updateBooking(Long id, Booking booking);
    void deleteBooking(Long id);
    Booking getBookingById(Long id);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUser(Long userId);
    List<Booking> getBookingsByApartment(Long apartmentId);
    boolean isApartmentAvailable(Long apartmentId, LocalDate checkIn, LocalDate checkOut);
    Booking changeBookingStatus(Long id, BookingStatus status);
    List<Booking> getBookingsByStatus(BookingStatus status);

    long countBookingsByStatus(BookingStatus status);
}

