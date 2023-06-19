package ru.bazzar.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.api.ReviewDto;
import ru.bazzar.core.entities.Review;
import ru.bazzar.core.repositories.ReviewRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository repository;
    private final ModelMapper modelMapper;

    public ReviewDto createOrUpdateReview(ReviewDto reviewDto) {
        Long id = reviewDto.getId();
        if (id == null) {
            Review review = modelMapper.map(reviewDto, Review.class);
            Review savedReview = repository.save(review);
            log.info("Отзыв оставлен пользователем " + reviewDto.getUsername());
            return modelMapper.map(savedReview, ReviewDto.class);
        }
        Review review = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Отзыв с таким id не найден"));
        review.setUsername(reviewDto.getUsername());
        review.setReviewText(reviewDto.getReviewText());
        review.setMark(reviewDto.getMark());
        log.info("Отзыв обновлен с идентификатором " + id);
        Review savedReview = repository.save(review);
        return modelMapper.map(savedReview, ReviewDto.class);
    }

    public ReviewDto findById(Long id) {
        Review review = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Отзыв с таким id не найден"));
        return modelMapper.map(review, ReviewDto.class);
    }

    public void deleteReview(Long id) {
        log.info("Отзыв c id " + id + " удален");
        repository.deleteById(id);
    }


}
