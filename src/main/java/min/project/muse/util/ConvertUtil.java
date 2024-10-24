package min.project.muse.util;

import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.comment.Comment;
import min.project.muse.domain.likes.Likes;
import min.project.muse.domain.music.Music;
import min.project.muse.web.dto.comment.ShowCommentResponse;
import min.project.muse.web.dto.music.ShowMusicResponse;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ConvertUtil {

    public static List<ShowMusicResponse> convertToMusicDto(List<Music> musics, long loginUserId) {

        List<ShowMusicResponse> res = new LinkedList<>();

        for (Music music : musics) {
            List<Likes> likes = music.getLikes();
            List<Comment> comments = music.getComments();

            List<ShowCommentResponse> commentDtoList = new LinkedList<>();

            for (Comment comment : comments)
                commentDtoList.add(convertToCommentDto(comment, loginUserId));


            res.add(ShowMusicResponse.builder()
                    .user(music.getUser())
                    .id(music.getId())
                    .title(music.getTitle())
                    .artist(music.getArtist())
                    .moods(Arrays.stream(music.getMoods().split(",")).toList())
                    .image(music.getImage())
                    .owner(music.getUser().getId() == loginUserId)
                    .likeCount(likes.size())
                    .likeStatus(likes.stream().anyMatch(like -> like.getUser().getId() == loginUserId))
                    .comments(commentDtoList)
                    .build()
            );
        }

        return res;

    }

    public static ShowCommentResponse convertToCommentDto(Comment commentEntity, long userId) {
        return ShowCommentResponse.builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .createDate(commentEntity.getCreateDate())
                .userId(commentEntity.getUser().getId())
                .username(commentEntity.getUser().getUsername())
                .musicId(commentEntity.getMusic().getId())
                .owner(userId == commentEntity.getUser().getId())
                .build();
    }
}
