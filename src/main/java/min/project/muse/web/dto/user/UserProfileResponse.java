package min.project.muse.web.dto.user;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import min.project.muse.domain.music.Music;

import java.util.List;

@Getter
@Builder
@Transactional
public class UserProfileResponse {

    private String username;
    private String email;
    private String nickname;
    private List<Music> musicList;

}
