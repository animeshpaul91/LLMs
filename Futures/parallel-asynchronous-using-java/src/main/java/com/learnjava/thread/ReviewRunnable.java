package com.learnjava.thread;

import com.learnjava.domain.Review;
import com.learnjava.service.ReviewService;

public class ReviewRunnable implements Runnable {
    private final ReviewService reviewService;
    private final String productId;
    private Review review;

    public ReviewRunnable(final ReviewService reviewService, final String productId) {
        this.reviewService = reviewService;
        this.productId = productId;
    }

    @Override
    public void run() {
        this.review = reviewService.retrieveReviews(productId); // blocking call
    }

    public Review getReview() {
        return review;
    }
}
