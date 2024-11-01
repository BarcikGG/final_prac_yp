package com.example.final_prac.controller;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.BookingStatus;
import com.example.final_prac.model.User;
import com.example.final_prac.model.Booking;
import com.example.final_prac.service.ApartmentService;
import com.example.final_prac.service.BookingService;
import com.example.final_prac.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final ApartmentService apartmentService;
    private final UserService userService;
    private final BookingService bookingService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalUsers", userService.getAllUsers().size());
        model.addAttribute("totalApartments", apartmentService.getAllApartments().size());
        model.addAttribute("totalBookings", bookingService.getAllBookings().size());
        return "admin/dashboard";
    }

    @GetMapping("/apartments")
    public String apartments(Model model) {
        model.addAttribute("apartments", apartmentService.getAllApartments());
        return "admin/apartments";
    }

    @GetMapping("/apartment/new")
    public String newApartment(Model model) {
        model.addAttribute("apartment", new Apartment());
        return "admin/apartment-form";
    }

    @PostMapping("/apartment/save")
    public String saveApartment(@Valid @ModelAttribute("apartment") Apartment apartment,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "admin/apartment-form";
        }

        if (apartment.getId() == null) {
            apartmentService.createApartment(apartment);
        } else {
            apartmentService.updateApartment(apartment.getId(), apartment);
        }

        return "redirect:/admin/apartments";
    }

    @GetMapping("/apartment/edit/{id}")
    public String editApartment(@PathVariable Long id, Model model) {
        model.addAttribute("apartment", apartmentService.getApartmentById(id));
        return "admin/apartment-form";
    }

    @PostMapping("/apartment/delete/{id}")
    public String deleteApartment(@PathVariable Long id) {
        apartmentService.deleteApartment(id);
        return "redirect:/admin/apartments";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/bookings")
    public String bookings(Model model) {
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "admin/bookings";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", BeanDefinitionDsl.Role.values());
        return "admin/user-form";
    }

    @PostMapping("/user/save")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/user-form";
        }
        userService.updateUser(user.getId(), user);
        return "redirect:/admin/users";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/booking/edit/{id}")
    public String editBooking(@PathVariable Long id, Model model) {
        model.addAttribute("booking", bookingService.getBookingById(id));
        model.addAttribute("statuses", BookingStatus.values());
        model.addAttribute("apartments", apartmentService.getAllApartments());
        model.addAttribute("users", userService.getAllUsers());
        return "admin/booking-form";
    }

    @PostMapping("/booking/save")
    public String saveBooking(@Valid @ModelAttribute("booking") Booking booking, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/booking-form";
        }
        bookingService.updateBooking(booking.getId(), booking);
        return "redirect:/admin/bookings";
    }

    @PostMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "redirect:/admin/bookings";
    }
}
