package kr.co.cofile.jwtitan.controller.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/events")
public class EventPaymentController {

    // 결제 생성
    @PostMapping("/registrations/{regId}/payments")
    public ResponseEntity<String> create(@PathVariable("regId") String regId) {
        return ResponseEntity.ok("post:events/registrations/" + regId + "/payments");
    }

    // 결제 정보 조회
    @GetMapping("/registrations/{regId}/payments")
    public ResponseEntity<String> get(@PathVariable("regId") String regId) {
        return ResponseEntity.ok("get:events/registrations/" + regId + "/payments");
    }

    // 환불 처리
    @GetMapping("/payments/{paymentId}/refund")
    public ResponseEntity<String> refund(@PathVariable("paymentId") String paymentId) {
        return ResponseEntity.ok("get:events/payments/" + paymentId + "/refund");
    }
}
