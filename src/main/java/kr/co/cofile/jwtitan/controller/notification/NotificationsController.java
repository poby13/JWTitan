package kr.co.cofile.jwtitan.controller.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationsController {

    // 알림 목록 조회
    @GetMapping
    public ResponseEntity<String> list() {
        return ResponseEntity.ok().body("get:notifications");
    }

    // 특정 알림 조회
    @GetMapping("/{notificationId}")
    public ResponseEntity<String> get(@PathVariable String notificationId) {
        return ResponseEntity.ok().body("get:notifications/" + notificationId);
    }

    // 알림 읽음 처리
    @PutMapping("/{notificationId}/read")
    public ResponseEntity<String> update(@PathVariable String notificationId) {
        return ResponseEntity.ok().body("put:notifications/" + notificationId + "/read");
    }

    // 알림 삭제
    @DeleteMapping("/{notificationId}")
    public ResponseEntity<String> delete(@PathVariable String notificationId) {
        return ResponseEntity.ok().body("delete:notifications/" + notificationId);
    }
}
