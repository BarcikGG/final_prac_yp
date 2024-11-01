package com.example.final_prac.service;

import com.example.final_prac.model.Review;
import com.example.final_prac.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow();
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Long id, Review reviewDetails) {
        Review review = getReviewById(id);
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByApartment(Long apartmentId) {
        return reviewRepository.findByApartmentId(apartmentId);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
