package min.project.muse.web.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.user.Role;
import min.project.muse.domain.user.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Slf4j
public class AddUserRequest {

    private String username;
    private String password;
    private String email;

    public User toEntity(String encodedPassword, Role role) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .role(role)
                .build();
    }
}
