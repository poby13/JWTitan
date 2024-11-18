package kr.co.cofile.jwtitan.controller.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventSubscriptionController {

    // 알림 구독
    @PostMapping("/{eventId}/subscriptions")
    public ResponseEntity<String> create(@PathVariable String eventId) {
        return ResponseEntity.ok("post:events/" + eventId + "/subscriptions");
    }

    // 구독 취소
    @DeleteMapping("/{eventId}/subscriptions")
    public ResponseEntity<String> delete(@PathVariable String eventId) {
        return ResponseEntity.ok("delete:events/" + eventId + "/subscriptions");
    }

    // 구독 설정 수정
    @PutMapping("/{eventId}/subscriptions")
    public ResponseEntity<String> update(@PathVariable String eventId) {
        return ResponseEntity.ok("put:events/" + eventId + "/subscriptions");
    }
}
