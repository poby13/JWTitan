package kr.co.cofile.jwtitan.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserContriller {

    // 사용자 목록 조회
    @GetMapping
    public ResponseEntity<String> list() {
        return ResponseEntity.ok("get:users");
    }

    // 특정 사용자 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<String> get(@PathVariable("userId") String userId) {
        return ResponseEntity.ok("get:users/" + userId);
    }

    // 사용자 정보 수정
    @PostMapping("/{userId}")
    public ResponseEntity<String> update(@PathVariable("userId") String userId) {
        return ResponseEntity.ok("post:users/" + userId);
    }

    // 사용자 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@PathVariable("userId") String userId) {
        return ResponseEntity.ok("delete:users/" + userId);
    }
}
