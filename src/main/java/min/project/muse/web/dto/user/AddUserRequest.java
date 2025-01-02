package min.project.muse.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.user.Role;
import min.project.muse.domain.user.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Slf4j
public class AddUserRequest {

    @NotBlank
    @Size(min = 3, max = 15, message = "3 ~ 15 범위에 맞는 유저명이 필요합니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.[A-Z])(?=.[a-z])(?=.\\d)(?=.[!@#$%^&(),.?\":{}|<>])[A-Za-z\\d!@#$%^&(),.?\":{}|<>]{8,}$"
            , message = "특수문자를 포함한 8자 이상의 비밀번호 필요")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,}$"
            , message = "이메일 형식을 확인해주세요")
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
