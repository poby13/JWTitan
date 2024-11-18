package kr.co.cofile.jwtitan.controller.category;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    // 카테고리 목록 조회
    @GetMapping("/categories")
    public ResponseEntity<String> list() {
        return ResponseEntity.ok("get:categories");
    }

    // 새 카테고리 생성
    @PostMapping("/categories")
    public ResponseEntity<String> create() {
        return ResponseEntity.ok("post:categories");
    }

    // 카테고리 수정
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<String> update(@PathVariable("categoryId") String categoryId) {
        return ResponseEntity.ok("post:categories/" + categoryId);
    }

    // 카테고리 삭제
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<String> delete(@PathVariable("categoryId") String categoryId) {
        return ResponseEntity.ok("delete:categories/" + categoryId);
    }

    // 카테고리별 이벤트 조회
    @GetMapping("/events/categories/{categoryId}")
    public ResponseEntity<String> events(@PathVariable("categoryId") String categoryId) {
        return ResponseEntity.ok("get:event/categories/" + categoryId);
    }
}
