package com.siva.review.Service.review.Impl;


import com.siva.review.Service.review.Review;
import com.siva.review.Service.review.ReviewRepository;
import com.siva.review.Service.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;


    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        List<Review> reviews =reviewRepository.findByCompanyId(companyId);
        return reviews;


    }

    @Override
    public boolean addReview(Long companyId, Review review) {

        if(companyId!=null && review != null){
            review.setCompanyId(companyId);
             reviewRepository.save(review);
             return true;
        }
        return false;
    }

    @Override
    public Review getReview( Long reviewId) {
        return reviewRepository.findById(reviewId).orElse((null));

    }

    @Override
    public boolean updateReview( Long reviewId, Review updatedreview) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(reviewId!= null){
            review.setTitle(updatedreview.getTitle());
            review.setDescription(updatedreview.getDescription());
            review.setRating(updatedreview.getRating());
            review.setCompanyId(updatedreview.getCompanyId());
            reviewRepository.save(updatedreview);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElse(null);
        if(review!=null) {
            reviewRepository.delete(review);

            return true;
        }

        return false;

    }
}
