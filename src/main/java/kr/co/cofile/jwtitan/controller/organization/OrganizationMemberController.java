package kr.co.cofile.jwtitan.controller.organization;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/organizations")
public class OrganizationMemberController {

    // 멤버 목록 조회
    @GetMapping("/{orgId}/members")
    public ResponseEntity<String> list(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok("get:organizations/" + orgId + "/members");
    }

    // 멤버 추가
    @PostMapping("/{orgId}/members")
    public ResponseEntity<String> create(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok("post:organizations/" + orgId + "/members");
    }

    // 멤버 제거
    @DeleteMapping("/{orgId}/members/{userId}")
    public ResponseEntity<String> delete(@PathVariable("orgId") String orgId,
                                         @PathVariable("userId") String userId) {
        return ResponseEntity.ok("delete:organizations/" + orgId + "/members/" + userId);
    }

    // 멤버 역할 변경
    @PutMapping("/{orgId}/members/{userId}/role")
    public ResponseEntity<String> updateRole(@PathVariable("orgId") String orgId,
                                             @PathVariable("userId") String userId) {
        return ResponseEntity.ok("put:organizations/" + orgId + "/members/" + userId);
    }

    // 멤버 역할 변경 이력
    @GetMapping("/{orgId}/members/{userId}/history")
    public ResponseEntity<String> history(@PathVariable("orgId") String orgId,
                                          @PathVariable("userId") String userId) {
        return ResponseEntity.ok("get:organizations/" + orgId + "/members/" + userId);
    }
}
