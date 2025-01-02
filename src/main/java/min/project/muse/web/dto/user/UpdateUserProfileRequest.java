package min.project.muse.web.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import min.project.muse.domain.user.User;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateUserProfileRequest {

    private MultipartFile profileImage;

    private String userId;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{1,8}$", message = "8자 이하의 특수 문자가 들어가지 않은 닉네임을 입력해주세요")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,}$"
            , message = "이메일 형식을 확인해주세요")
    private String email;

    private String bio;

}
