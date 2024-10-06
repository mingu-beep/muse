package min.project.muse.web.dto.music;

import lombok.Getter;
import min.project.muse.domain.music.Music;

@Getter
public class MusicResponse {

    private final String title;
    private final String artist;

    public MusicResponse(Music music) {
        this.title = music.getTitle();
        this.artist = music.getArtist();
    }

}
