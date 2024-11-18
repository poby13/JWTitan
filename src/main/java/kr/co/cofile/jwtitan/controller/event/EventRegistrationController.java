package kr.co.cofile.jwtitan.controller.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventRegistrationController {

    // 등록 목록 조회
    @GetMapping("/{eventId}/registrations")
    public ResponseEntity<String> list(@PathVariable String eventId) {
        return ResponseEntity.ok("get:events/" + eventId + "/registrations");
    }

    // 등록 신청
    @PostMapping("/{eventId}/registrations")
    public ResponseEntity<String> create(@PathVariable String eventId) {
        return ResponseEntity.ok("post:events/" + eventId + "/registrations");
    }

    // 등록 상태 변경
    @PutMapping("/{eventId}/registrations/{regId}/status")
    public ResponseEntity<String> update(@PathVariable String eventId, @PathVariable String regId) {
        return ResponseEntity.ok("put:events/" + eventId + "/registrations" + regId + "/status");
    }
}
