package kr.co.cofile.jwtitan.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.List;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    @Email
    private String email;
    private Boolean enabled;
    private Long groupId;
    private List<Role> roleList;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                '}';  // password는 보안상 제외
    }
}