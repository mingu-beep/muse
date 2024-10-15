package min.project.muse.web.dto.music;

import jakarta.persistence.Convert;
import lombok.Builder;
import lombok.Getter;
import min.project.muse.domain.user.User;
import min.project.muse.util.StringListConverter;

import java.util.List;

@Getter
@Builder
public class ShowMusicResponse {

    private long id;
    private String image;

    private String title;
    private String artist;

    @Convert(converter = StringListConverter.class)
    private List<String> moods;

    private String details;

    private User user;

    private Boolean owner;

}
