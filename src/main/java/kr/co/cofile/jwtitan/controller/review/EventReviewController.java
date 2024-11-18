package kr.co.cofile.jwtitan.controller.review;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventReviewController {

    // 이벤트 리뷰 목록 조회
    @GetMapping("/{eventId}/reviews")
    public ResponseEntity<String> list(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok("get:events/" + eventId + "/reviews");
    }

    // 리뷰 작성
    @PostMapping("/{eventId}/reviews")
    public ResponseEntity<String> create(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok("post:events/" + eventId + "/reviews");
    }

    // 리뷰 수정
    @PutMapping("/{eventId}/reviews/{reviewId}")
    public ResponseEntity<String> update(@PathVariable("eventId") String eventId
            , @PathVariable("reviewId") String reviewId) {
        return ResponseEntity.ok("update:events/" + eventId + "/reviews/" + reviewId);
    }

    // 리뷰 삭제
    @DeleteMapping("/{eventId}/reviews/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable("eventId") String eventId
            , @PathVariable("reviewId") String reviewId) {
        return ResponseEntity.ok("delete:events/" + eventId + "/reviews/" + reviewId);
    }
}
