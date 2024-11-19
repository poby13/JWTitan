package kr.co.cofile.jwtitan.controller.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventParticipationController {

    // 참가자 목록 조회
    @GetMapping("/{eventId}/participations")
    public ResponseEntity<String> list(@PathVariable String eventId) {
        return ResponseEntity.ok("get:participations/" + eventId);
    }

    // 참가 신청
    @PostMapping("/{eventId}/participations")
    public ResponseEntity<String> create(@PathVariable String eventId) {
        return ResponseEntity.ok("post:participations/" + eventId);
    }

    // 참가 취소
    @DeleteMapping("/{eventId}/participations/{userId}")
    public ResponseEntity<String> delete(@PathVariable String eventId, @PathVariable String userId) {
        return ResponseEntity.ok("delete:participations/" + eventId + "/" + userId);
    }

    // 참가 상태 변경
    @PutMapping("/{eventId}/participations/{userId}/status")
    public ResponseEntity<String> update(@PathVariable String eventId, @PathVariable String userId) {
        return ResponseEntity.ok("update:participations/" + eventId + "/" + userId);
    }
}
