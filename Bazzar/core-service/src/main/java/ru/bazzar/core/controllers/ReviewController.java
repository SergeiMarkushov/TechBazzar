package ru.bazzar.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bazzar.core.api.ReviewDto;
import ru.bazzar.core.services.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
    }

    @GetMapping("/{id}")
    public ReviewDto findById(@PathVariable("id") Long id) {
        return reviewService.findById(id);
    }

    @PostMapping
    public ReviewDto createOrUpdateReview(@RequestBody ReviewDto reviewDto) {
        return reviewService.createOrUpdateReview(reviewDto);
    }
    @GetMapping("/find-by-productId/{productId}")
    public List<ReviewDto> findByProductId(@PathVariable Long productId) {
        return reviewService.findByProductId(productId);
    }
}
