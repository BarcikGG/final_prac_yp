package com.example.final_prac.controller;

import com.example.final_prac.model.Booking;
import com.example.final_prac.model.BookingStatus;
import com.example.final_prac.service.ApartmentService;
import com.example.final_prac.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager")
@PreAuthorize("hasRole('MANAGER')")
@RequiredArgsConstructor
public class ManagerController {

    private final BookingService bookingService;
    private final ApartmentService apartmentService;

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) BookingStatus status, Model model) {
        List<Booking> bookings;
        if (status != null) {
            bookings = bookingService.getBookingsByStatus(status);
        } else {
            bookings = bookingService.getAllBookings();
        }

        // Подсчет статистики
        long confirmedCount = bookingService.countBookingsByStatus(BookingStatus.CONFIRMED);
        long pendingCount = bookingService.countBookingsByStatus(BookingStatus.PENDING);
        long completedCount = bookingService.countBookingsByStatus(BookingStatus.COMPLETED);

        model.addAttribute("bookings", bookings);
        model.addAttribute("confirmedCount", confirmedCount);
        model.addAttribute("pendingCount", pendingCount);
        model.addAttribute("completedCount", completedCount);
        return "manager/dashboard";
    }

    @PostMapping("/booking/{id}/status")
    public String updateBookingStatus(@PathVariable Long id,
                                      @RequestParam BookingStatus status) {
        bookingService.changeBookingStatus(id, status);
        return "redirect:/manager/dashboard";
    }
}
