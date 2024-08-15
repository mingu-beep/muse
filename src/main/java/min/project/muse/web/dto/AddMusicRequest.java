package min.project.muse.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import min.project.muse.domain.music.Music;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddMusicRequest {

//    private String image;
    private String title;
    private String singer;
    private String mood;
    private String content;

    public Music toEntity() {
        return Music.builder()
                .title(title)
                .singer(singer)
                .mood(mood)
                .content(content)
                .build();
    }
}
