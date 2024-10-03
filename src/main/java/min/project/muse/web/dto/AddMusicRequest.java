package min.project.muse.web.dto;

import jakarta.persistence.Convert;
import lombok.*;
import min.project.muse.domain.music.Music;
import min.project.muse.util.StringListConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddMusicRequest {

    private String writer;
    private String title;
    private String artist;
    private MultipartFile image;
    private List<String> mood;

    private String details;

    public Music toEntity(String imageUrl) {
        return Music.builder()
                .writer(writer)
                .title(title)
                .artist(artist)
                .mood(mood)
                .details(details)
                .image(imageUrl)
                .build();
    }
}
