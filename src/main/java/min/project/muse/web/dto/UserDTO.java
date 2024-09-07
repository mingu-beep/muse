package min.project.muse.web.dto;

import lombok.*;
import min.project.muse.domain.user.User;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;

    public static UserDTO toDTO (User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    public User toEntity() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
