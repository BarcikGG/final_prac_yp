package com.example.final_prac.controller;

import com.example.final_prac.model.Apartment;
import com.example.final_prac.model.Booking;
import com.example.final_prac.model.Review;
import com.example.final_prac.model.User;
import com.example.final_prac.service.ApartmentService;
import com.example.final_prac.service.BookingService;
import com.example.final_prac.service.ReviewService;
import com.example.final_prac.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ApartmentService apartmentService;
    private final UserService userService;
    private final BookingService bookingService;
    private final ReviewService reviewService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "auth";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "auth";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, Model model, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("user", new User());
            return "auth";
        }
        userService.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/apartment/{id}")
    public String apartmentDetails(@PathVariable Long id, Model model) {
        Apartment apartment = apartmentService.getApartmentById(id);
        model.addAttribute("apartment", apartment);
        model.addAttribute("reviews", reviewService.getReviewsByApartment(id));
        model.addAttribute("newReview", new Review());
        return "apartment-details";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/booking/{apartmentId}")
    public String showBookingForm(@PathVariable Long apartmentId, Model model) {
        model.addAttribute("apartment", apartmentService.getApartmentById(apartmentId));
        model.addAttribute("booking", new Booking());
        return "booking-form";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/booking/{apartmentId}")
    public String createBooking(@PathVariable Long apartmentId,
                                @Valid @ModelAttribute("booking") Booking booking,
                                BindingResult result,
                                Model model,
                                Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("apartment", apartmentService.getApartmentById(apartmentId));
            System.out.println(result);
            return "booking-form";
        }

        User user = userService.getUserByUsername(principal.getName());
        Apartment apartment = apartmentService.getApartmentById(apartmentId);

        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        BigDecimal totalPrice = apartment.getPricePerNight().multiply(BigDecimal.valueOf(nights));
        booking.setTotalPrice(totalPrice);

        booking.setUser(user);
        booking.setApartment(apartment);
        bookingService.createBooking(booking);

        return "redirect:/my-bookings";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/my-bookings")
    public String myBookings(Principal principal, Model model) {
        User user = userService.getUserByUsername(principal.getName());
        List<Booking> bookings = bookingService.getBookingsByUser(user.getId());

        model.addAttribute("bookings", bookings);
        return "my-bookings";
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/review/{apartmentId}")
    public String addReview(@PathVariable Long apartmentId,
                            @Valid @ModelAttribute("review") Review review,
                            BindingResult result,
                            Principal principal) {
        if (result.hasErrors()) {
            return "redirect:/apartment/" + apartmentId;
        }

        User user = userService.getUserByUsername(principal.getName());
        Apartment apartment = apartmentService.getApartmentById(apartmentId);

        review.setUser(user);
        review.setApartment(apartment);
        reviewService.createReview(review);

        return "redirect:/apartment/" + apartmentId;
    }
}