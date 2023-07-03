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
        if (reviewDto.getId() == null) {
            Review review = repository.save(modelMapper.map(reviewDto, Review.class));
            log.info("Отзыв оставлен пользователем " + reviewDto.getUsername());
            return modelMapper.map(review, ReviewDto.class);
        }
        Review review = repository.findById(reviewDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Отзыв с таким id не найден"));
        review.setReviewText(reviewDto.getReviewText());
        review.setMark(reviewDto.getMark());
        return modelMapper.map(repository.save(review), ReviewDto.class);
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

    public Integer countByProductId(Long productId) {
        Integer count = repository.countByProductId(productId);
        if (count != null) {
            return count;
        } else {
            return 0;
        }
    }
}
