package kr.co.cofile.jwtitan.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final Long groupId;

    public CustomAuthenticationToken(String username, String password, Long groupId) {
        super(username, password);
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }
}