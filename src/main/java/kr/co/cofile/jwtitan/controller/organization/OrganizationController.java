package kr.co.cofile.jwtitan.controller.organization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orgaizations")
public class OrganizationController {

    // 조직 목록 조회
    @GetMapping
    public ResponseEntity<String> list() {
        return ResponseEntity.ok("get:organizations");
    }

    // 새 조직 생성
    @PostMapping
    public ResponseEntity<String> create() {
        return ResponseEntity.ok("post:organizations");
    }

    // 특정 조직 정보 조회
    @GetMapping("/{orgId}")
    public ResponseEntity<String> get(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok("get:organizations/" + orgId);
    }

    // 조직 정보 수정
    @PutMapping("/{orgId}")
    public ResponseEntity<String> update(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok("get:organizations/" + orgId);
    }

    // 조직 삭제
    @DeleteMapping("/{orgId}")
    public ResponseEntity<String> delete(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok("get:organizations/" + orgId);
    }
}
