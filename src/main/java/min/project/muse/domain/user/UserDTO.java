package min.project.muse.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDTO {

    long id;
    String username;
    String nickname;
    String profileImage;

}
