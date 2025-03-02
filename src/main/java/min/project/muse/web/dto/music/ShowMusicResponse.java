package min.project.muse.web.dto.music;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import min.project.muse.domain.mood.Mood;
import min.project.muse.domain.mood.MoodDTO;
import min.project.muse.domain.user.User;
import min.project.muse.domain.user.UserDTO;
import min.project.muse.web.dto.comment.ShowCommentResponse;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ShowMusicResponse {

    private long id;
    private String image;

    private String title;
    private String artist;

//    @Convert(converter = StringListConverter.class)
    private Set<MoodDTO> moods;

    private String details;

    private UserDTO user;


    private Boolean owner;

    private Boolean likeStatus;
    private Integer likeCount;

    private List<ShowCommentResponse> comments;

}
