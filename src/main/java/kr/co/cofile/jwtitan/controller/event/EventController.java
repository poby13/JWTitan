package kr.co.cofile.jwtitan.controller.event;

import kr.co.cofile.jwtitan.dto.EventRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    // 이벤트 목록 조회
    @GetMapping
    public ResponseEntity<String> list() {
        return ResponseEntity.ok("get:events");
    }

    // 새 이벤트 생성
    @PostMapping
    public ResponseEntity<String> create() {
        return ResponseEntity.ok("post:events");
    }

    // 특정 이벤트 조회
    @GetMapping("/{eventId}")
    public ResponseEntity<String> get(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok("get:events/" + eventId);
    }

    // 이벤트 정보 수정
    @PutMapping("/{eventId}")
    public ResponseEntity<String> update(@PathVariable("eventId") String eventId, @RequestBody EventRequest eventRequest) {
        return ResponseEntity.ok("put:events/" + eventId);
    }

    // 이벤트 삭제
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> delete(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok("delete:events/" + eventId);
    }
}
