package com.example.final_prac.controller;

import com.example.final_prac.model.Booking;
import com.example.final_prac.model.BookingStatus;
import com.example.final_prac.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingRestController {

    private final BookingService bookingService;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.createBooking(booking));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id,
                                                 @Valid @RequestBody Booking booking) {
        return ResponseEntity.ok(bookingService.updateBooking(id, booking));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id,
                                                       @RequestParam BookingStatus status) {
        return ResponseEntity.ok(bookingService.changeBookingStatus(id, status));
    }
}
