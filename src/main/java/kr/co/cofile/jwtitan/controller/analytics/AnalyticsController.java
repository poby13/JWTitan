package kr.co.cofile.jwtitan.controller.analytics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    // 이벤트 요약 통계
    @GetMapping("/events/{eventId}/summary")
    public ResponseEntity<String> getEventSummary(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok("get:analytics/events/" + eventId + "/summary");
    }

    // 참가자 통계
    @GetMapping("/events/{eventId}/participations")
    public ResponseEntity<String> getEventParticipations(@PathVariable("eventId") String eventId) {
        return ResponseEntity.ok("get:analytics/events/" + eventId + "/participations");
    }

    // 조직 이벤트 통계
    @GetMapping("/organizations/{orgId}/events")
    public ResponseEntity<String> getOrganizationEvents(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok("get:analytics/organizations/" + orgId + "/");
    }

    // 사용자 참가 통계
    @GetMapping("/users/{userId}/participations")
    public ResponseEntity<String> getUserParticipation(@PathVariable("orgId") String orgId) {
        return ResponseEntity.ok("get:analytics/organizations/" + orgId + "/events");
    }
}
