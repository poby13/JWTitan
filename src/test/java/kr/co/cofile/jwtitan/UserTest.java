package kr.co.cofile.jwtitan;

import kr.co.cofile.jwtitan.model.User;
import kr.co.cofile.jwtitan.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void test() {
        User user = new User();
        user.setUsername("test2");
        user.setPassword(passwordEncoder.encode("1234"));
        user.setEmail("test@test.com");

        userMapper.save(user);

        Integer roleId = userMapper.findRoleIdByName("ROLE_USER");

        userMapper.saveUserRoles(user.getId(), roleId);
    }

}
