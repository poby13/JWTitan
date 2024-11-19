package kr.co.cofile.jwtitan.controller.files;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class FilesController {

    // 파일 업로드
    @PostMapping("/files/upload")
    public ResponseEntity<String> create() {
        return ResponseEntity.ok("post:files/upload");
    }

    // 파일 다운로드
    @GetMapping("/files/{fileId}")
    public ResponseEntity<String> get(@PathVariable("fileId") String fileId) {
        return ResponseEntity.ok("get:files/" + fileId);
    }

    // 파일 삭제
    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<String> delete(@PathVariable("fileId") String fileId) {
        return ResponseEntity.ok("delete:files/" + fileId);
    }

    // 이벤트 첨부파일 목록
    @GetMapping("/events/{eventId}/files")
    public ResponseEntity<String> eventFiles(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok("get:events/" + eventId + "/files");
    }

    // 리뷰 첨부파일 목록
    @GetMapping("/reviews/{reviewId}/files")
    public ResponseEntity<String> reviewFiles(@PathVariable("reviewId") String reviewId) {
        return ResponseEntity.ok("get:reviews/" + reviewId + "/files");
    }
}
