package min.project.muse.web.dto.user;

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
    private String nickname;
    private String email;
    private String bio;

}
