package kr.co.cofile.jwtitan.controller.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    // 좋아요 추가
    @PostMapping("/{reviewId}/likes")
    public ResponseEntity<String> create(@PathVariable String reviewId) {
        return ResponseEntity.ok("post:reviews/" + reviewId + "/likes");
    }

    // 좋아요 취소
    @DeleteMapping("/{reviewId}/likes")
    public ResponseEntity<String> delete(@PathVariable String reviewId) {
        return ResponseEntity.ok("delete:reviews/" + reviewId + "/likes");
    }
}
