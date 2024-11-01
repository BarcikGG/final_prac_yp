package com.example.final_prac.service;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.Booking;
import com.example.final_prac.model.BookingStatus;
import com.example.final_prac.model.User;
import com.example.final_prac.repository.ApartmentRepository;
import com.example.final_prac.repository.BookingRepository;
import com.example.final_prac.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ApartmentRepository apartmentRepository;

    @Override
    public Booking createBooking(Booking booking) {
        validateBookingDates(booking);
        if (!isApartmentAvailable(booking.getApartment().getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate())) {
            throw new RuntimeException("Apartment is not available for selected dates");
        }
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Long id, Booking bookingDetails) {
        Booking booking = getBookingById(id);

        if (!booking.getCheckInDate().equals(bookingDetails.getCheckInDate()) ||
                !booking.getCheckOutDate().equals(bookingDetails.getCheckOutDate())) {
            validateBookingDates(bookingDetails);
            if (!isApartmentAvailable(booking.getApartment().getId(),
                    bookingDetails.getCheckInDate(),
                    bookingDetails.getCheckOutDate())) {
                throw new RuntimeException("Apartment is not available for selected dates");
            }
        }

        booking.setCheckInDate(bookingDetails.getCheckInDate());
        booking.setCheckOutDate(bookingDetails.getCheckOutDate());
        booking.setTotalPrice(bookingDetails.getTotalPrice());
        booking.setNotes(bookingDetails.getNotes());

        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .filter(booking -> booking.getStatus() == BookingStatus.CONFIRMED || booking.getStatus() == BookingStatus.COMPLETED)
                .collect(Collectors.toList());
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    public long countBookingsByStatus(BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }

    @Override
    public List<Booking> getBookingsByApartment(Long apartmentId) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));
        return bookingRepository.findByApartment(apartment);
    }

    @Override
    public boolean isApartmentAvailable(Long apartmentId, LocalDate checkIn, LocalDate checkOut) {
        Apartment apartment = apartmentRepository.findById(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment not found"));

        List<Booking> overlappingBookings = bookingRepository
                .findByApartmentAndCheckInDateGreaterThanEqualAndCheckOutDateLessThanEqual(
                        apartment, checkIn, checkOut);

        return overlappingBookings.isEmpty();
    }

    @Override
    public Booking changeBookingStatus(Long id, BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    private void validateBookingDates(Booking booking) {
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new RuntimeException("Check-in date must be before check-out date");
        }
        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }
    }
}
