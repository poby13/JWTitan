package kr.co.cofile.jwtitan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// todo 유효성검사
public class JwtRequest {
    private String username;
    private String password;
    private Long groupId;
}
