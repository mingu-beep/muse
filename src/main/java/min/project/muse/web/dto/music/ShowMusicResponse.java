package min.project.muse.web.dto.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import min.project.muse.domain.comment.Comment;
import min.project.muse.domain.likes.Likes;
import min.project.muse.domain.mood.Mood;
import min.project.muse.domain.mood.MoodDTO;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.User;
import min.project.muse.domain.user.UserDTO;
import min.project.muse.util.ConvertUtil;
import min.project.muse.web.dto.comment.ShowCommentResponse;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
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

    public ShowMusicResponse(Music music, long loginUserId) {


        List<Likes> likes = music.getLikes();
        List<Comment> comments = music.getComments();

        List<ShowCommentResponse> commentDtoList = new LinkedList<>();

        for (Comment comment : comments)
            commentDtoList.add(ConvertUtil.convertToCommentDto(comment, loginUserId));


        this.id = music.getId();

        this.image = music.getImage();

        this.title = music.getTitle();
        this.artist = music.getArtist();

        this.moods = ConvertUtil.convertMoodDTO(music.getMoods());
        this.details = music.getDetails();

        this.user = ConvertUtil.convertToUserDTO(music.getUser());

        this.owner = (music.getUser().getId() == loginUserId);

        this.likeStatus = likes.stream().anyMatch(like -> like.getUser().getId() == loginUserId);
        this.likeCount = likes.size();

        this.comments = commentDtoList;

    }

}
