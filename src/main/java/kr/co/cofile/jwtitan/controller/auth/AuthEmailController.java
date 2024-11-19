package kr.co.cofile.jwtitan.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/email/verify")
public class AuthEmailController {

    // 이메일 인증 요청
    @PostMapping("/request")
    public ResponseEntity<String> request() {
        return ResponseEntity.ok("post:auth/email/verify/requst");
    }

    // 이메일 인증 확인
    @PostMapping("/confirm")
    public ResponseEntity<String> confirm() {
        return ResponseEntity.ok("post:auth/email/verify/confirm");
    }

    // 인증 메일 재발송
    @PostMapping("/resend")
    public ResponseEntity<String> resend() {
        return ResponseEntity.ok("post:auth/email/verify/resend");
    }

    // 인증 상태 확인
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("get:auth/email/verify/status");
    }
}
