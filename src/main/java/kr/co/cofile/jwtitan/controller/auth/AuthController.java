package kr.co.cofile.jwtitan.controller.auth;

import kr.co.cofile.jwtitan.dto.JwtRequest;
import kr.co.cofile.jwtitan.dto.JwtResponse;
import kr.co.cofile.jwtitan.dto.RefreshTokenRequest;
import kr.co.cofile.jwtitan.model.User;
import kr.co.cofile.jwtitan.mapper.UserMapper;
import kr.co.cofile.jwtitan.security.CustomAuthenticationToken;
import kr.co.cofile.jwtitan.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserMapper userMapper;
    private final RedisTemplate<String, String> redisTemplate;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup() {
        return ResponseEntity.ok("auth/singup");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        // AuthenticationManager - Provider 관리 - 적절한 Provider에게 인증 위임
        // CustomAuthenticationToken 사용
        authenticationManager.authenticate(
                //new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
                new CustomAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword(),
                        authenticationRequest.getGroupId()
                )
        );

        User user = userMapper.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String fingerprint = UUID.randomUUID().toString();
        String token = jwtTokenUtil.generateToken(user.getUsername(), user.getGroupId(), fingerprint);
        String refreshToken = UUID.randomUUID().toString();

        redisTemplate.opsForValue().set("refresh_token:" + refreshToken, user.getUsername(), 24, TimeUnit.HOURS);

        JwtResponse jwtResponse = JwtResponse.builder()
                .token(token)
                .username(user.getUsername())
                .refreshToken(refreshToken)
                .fingerprint(fingerprint)
                .build();

        return ResponseEntity.ok(jwtResponse);
    }

    // 토큰 갱신
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        String username = redisTemplate.opsForValue().get("refresh_token:" + request.getRefreshToken());
        if (username != null) {
            User user = userMapper.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String fingerprint = UUID.randomUUID().toString();
            String newToken = jwtTokenUtil.generateToken(username, user.getGroupId(), fingerprint);

            JwtResponse jwtResponse = JwtResponse.builder()
                    .token(newToken)
                    .refreshToken(request.getRefreshToken())
                    .fingerprint(fingerprint)
                    .build();

            return ResponseEntity.ok(jwtResponse);
        }
        return ResponseEntity.badRequest().body("Invalid refresh token");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") RefreshTokenRequest refreshToken) {
        String token = refreshToken.getRefreshToken();
        String fingerprint = refreshToken.getFingerprint();

        // 토큰이 null이거나 형식이 잘못된 경우 체크
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Invalid token format");
        }

        try {
            String jwtToken = token.substring(7);

            // 토큰 유효성 검증
            if (!jwtTokenUtil.validateToken(jwtToken, fingerprint)) {
                return ResponseEntity.badRequest().body("Invalid token");
            }

            // Redis에 블랙리스트로 등록
            long remainingTime = jwtTokenUtil.getExpirationDateFromToken(jwtToken).getTime()
                    - System.currentTimeMillis();

            if (remainingTime > 0) {
                redisTemplate.opsForValue().set(
                        "revoked_token:" + jwtToken,
                        "revoked",
                        jwtTokenUtil.getExpirationDateFromToken(jwtToken).getTime() - System.currentTimeMillis(),
                        TimeUnit.MILLISECONDS
                );
            }

            return ResponseEntity.ok("Successfully logged out");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Logout failed");
        }
    }

    // 비밀번호 초기화 요청
    @PostMapping("/password/reset")
    public ResponseEntity<String> passwordReset() {
        return ResponseEntity.ok("auth/passwordReset");
    }

    // 비밀번호 변경
    @PostMapping("/password/change")
    public ResponseEntity<String> passwordChange() {
        return ResponseEntity.ok("auth/passwordChange");
    }
}