package kr.co.cofile.jwtitan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    private String refreshToken;
    // 빌더 사용시에 기본값 적용
    @Builder.Default
    private String type = "Bearer";
    private String username;
    private String fingerprint;
}
