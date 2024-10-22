package min.project.muse.util;

import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.likes.Likes;
import min.project.muse.domain.music.Music;
import min.project.muse.web.dto.music.ShowMusicResponse;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class MusicConvertUtil {

    public static List<ShowMusicResponse> convertToMusicDto(List<Music> musics, long loginUserId) {

        List<ShowMusicResponse> res = new LinkedList<>();

        for (Music music : musics) {
            List<Likes> likes = music.getLikes();

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
                    .build()
            );
        }

        return res;

    }
}
