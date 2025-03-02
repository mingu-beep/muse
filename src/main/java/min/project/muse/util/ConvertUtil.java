package min.project.muse.util;

import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.comment.Comment;
import min.project.muse.domain.likes.Likes;
import min.project.muse.domain.mood.Mood;
import min.project.muse.domain.mood.MoodDTO;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.User;
import min.project.muse.domain.user.UserDTO;
import min.project.muse.web.dto.comment.ShowCommentResponse;
import min.project.muse.web.dto.music.ShowBriefMusicInfoResponse;
import min.project.muse.web.dto.music.ShowMusicResponse;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class ConvertUtil {

    public static List<ShowBriefMusicInfoResponse> getBriefInfo(List<Music> musics) {

        List<ShowBriefMusicInfoResponse> res = new LinkedList<>();

        for (Music music : musics) {
            List<Likes> likes = music.getLikes();

            res.add(ShowBriefMusicInfoResponse.builder()
                    .user(music.getUser())
                    .id(music.getId())
                    .title(music.getTitle())
                    .artist(music.getArtist())
                    .moods(music.getMoods().stream().map(Mood::getLabel).toList())
                    .likeCount(likes.size())
                    .build()
            );
        }

        return res;

    }

    public static UserDTO convertToUserDTO(User user) {

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .profileImage(user.getProfileImage())
                .nickname(user.getNickname())
                .build();
    }

    public static List<ShowMusicResponse> convertToMusicDto(List<Music> musics, long loginUserId) {

        List<ShowMusicResponse> res = new LinkedList<>();

        for (Music music : musics) {
            List<Likes> likes = music.getLikes();
            List<Comment> comments = music.getComments();

            List<ShowCommentResponse> commentDtoList = new LinkedList<>();

            for (Comment comment : comments)
                commentDtoList.add(convertToCommentDto(comment, loginUserId));


            res.add(ShowMusicResponse.builder()
                    .user(convertToUserDTO(music.getUser()))
                    .id(music.getId())
                    .title(music.getTitle())
                    .artist(music.getArtist())
                    .moods(convertMoodDTO(music.getMoods()))
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

    public static Set<MoodDTO> convertMoodDTO(Set<Mood> moods) {

        Set<MoodDTO> moodDTOSet = new HashSet<>();

        for (Mood mood : moods) {
            moodDTOSet.add(MoodDTO.builder()
                    .id(mood.getId())
                    .label(mood.getLabel())
                    .script(mood.getScript())
                    .build());
        }

        return moodDTOSet;
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
