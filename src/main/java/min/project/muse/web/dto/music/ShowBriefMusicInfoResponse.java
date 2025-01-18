package min.project.muse.web.dto.music;

import jakarta.persistence.Convert;
import lombok.Builder;
import lombok.Getter;
import min.project.muse.domain.mood.Mood;
import min.project.muse.domain.user.User;
import min.project.muse.util.StringListConverter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class ShowBriefMusicInfoResponse {

    private long id;

    private String title;
    private String artist;

    @Convert(converter = StringListConverter.class)
    private List<String> moods;

    private User user;

    private Integer likeCount;


}
