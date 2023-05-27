package ru.bazzar.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bazzar.core.repositories.ReviewRepository;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository repository;
}
