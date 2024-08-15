package min.project.muse.web.dto;

import lombok.Getter;
import min.project.muse.domain.music.Music;

@Getter
public class MusicResponse {

    private final String title;
    private final String singer;

    public MusicResponse(Music music) {
        this.title = music.getTitle();
        this.singer = music.getSinger();
    }

}
