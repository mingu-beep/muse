package min.project.muse.web.dto.user;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.Getter;
import min.project.muse.domain.music.Music;
import min.project.muse.web.dto.music.ShowMusicResponse;

import java.util.List;

@Getter
@Builder
@Transactional
public class UserProfileResponse {

    private long userId;
    private String profileImage;
    private String username;
    private String email;
    private String nickname;
    private String bio;

    private boolean owner;
    private Integer musicCount;
    private List<ShowMusicResponse> musicList;

}
