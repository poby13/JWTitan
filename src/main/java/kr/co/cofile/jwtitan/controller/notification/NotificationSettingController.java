package kr.co.cofile.jwtitan.controller.notification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/notification-settings")
public class NotificationSettingController {

    // 알림 설정 조회
    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("get:users/notification-settings");
    }

    // 알림 설정 수정
    @PutMapping
    public ResponseEntity<String> update() {
        return ResponseEntity.ok("put:users/notification-settings");
    }
}
