package min.project.muse.web.dto;

import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import min.project.muse.domain.music.Music;
import min.project.muse.util.StringListConverter;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AddMusicRequest {

//    private String image;
    private String title;
    private String artist;
    private List<String> mood;

    private String details;

    public Music toEntity() {
        return Music.builder()
                .title(title)
                .artist(artist)
                .mood(mood)
                .details(details)
                .build();
    }
}
