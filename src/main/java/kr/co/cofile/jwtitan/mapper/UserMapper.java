package kr.co.cofile.jwtitan.mapper;

import kr.co.cofile.jwtitan.dto.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper {
//    @Select("SELECT * FROM users WHERE username = #{username}")
    Optional<User> findByUsername(String username);

    @Insert("INSERT INTO users (username, password, email, enabled) " +
            "VALUES (#{username}, #{password}, #{email}, #{enabled})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Insert("INSERT INTO user_roles (user_id, role_id) values (#{userId}, #{roleId})")
    void saveUserRoles(Long userId, Integer roleId);

    @Select("SELECT id FROM roles where name = #{name}")
    Integer findRoleIdByName(String name);
}
