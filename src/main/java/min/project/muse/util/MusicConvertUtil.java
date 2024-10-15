package min.project.muse.util;

import min.project.muse.domain.music.Music;
import min.project.muse.web.dto.music.ShowMusicResponse;

import java.util.LinkedList;
import java.util.List;

public class MusicConvertUtil {

    public static List<ShowMusicResponse> convertToMusicDto(List<Music> musics, long loginUserId) {

        List<ShowMusicResponse> res = new LinkedList<>();

        for (Music music : musics) {
            res.add(ShowMusicResponse.builder()
                    .user(music.getUser())
                    .id(music.getId())
                    .title(music.getTitle())
                    .artist(music.getArtist())
                    .moods(music.getMoods())
                    .image(music.getImage())
                    .owner(music.getUser().getId() == loginUserId)
                    .build());
        }

        return res;

    }
}
