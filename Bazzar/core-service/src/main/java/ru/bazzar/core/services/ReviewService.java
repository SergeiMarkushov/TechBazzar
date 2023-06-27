package ru.bazzar.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.api.ResourceNotFoundException;
import ru.bazzar.core.api.ReviewDto;
import ru.bazzar.core.entities.Product;
import ru.bazzar.core.entities.Review;
import ru.bazzar.core.repositories.ProductRepository;
import ru.bazzar.core.repositories.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository repository;
    private final ProductRepository productRepository;
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
        review.setProduct(productRepository.findById(reviewDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден")));
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
    public List<ReviewDto> findByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Продукт не найден!"));
        return product.getReviews().stream().map((element) -> modelMapper.map(element, ReviewDto.class)).collect(Collectors.toList());
    }
}
