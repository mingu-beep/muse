package min.project.muse.web.dto;

import lombok.*;
import min.project.muse.domain.user.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AddUserRequest {

    private String username;
    private String password;
    private String email;

    public User toEntity(String encodedPassword, List<String> roles) {
        return User.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .roles(roles)
                .build();
    }
}
