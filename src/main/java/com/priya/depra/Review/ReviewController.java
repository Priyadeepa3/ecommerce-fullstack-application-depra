package com.priya.depra.Review;

import com.priya.depra.User.User;
import com.priya.depra.User.UserRepository;
import com.priya.depra.User.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponsedto>> getReviewsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(reviewService.getReviewsByProduct(productId));
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewResponsedto> addReview(
            Authentication authentication,
            @RequestBody CreateReviewdto request
    ) {

        String email = authentication.getName();

        System.out.println("🔥 Review POST received! Email: " + authentication.getName());

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(reviewService.addReview(user.getId(), request));
    }
}