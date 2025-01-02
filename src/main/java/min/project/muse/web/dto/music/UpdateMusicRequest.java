package min.project.muse.web.dto.music;

import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import min.project.muse.domain.music.Music;
import min.project.muse.util.StringListConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateMusicRequest {

    @NotBlank
    private MultipartFile image;

    @NotBlank
    private String title;

    @NotBlank
    private String artist;

    private String details;

//    @Convert(converter = StringListConverter.class)
    private String moods;


}
