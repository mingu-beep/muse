package min.project.muse.web.dto.music;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
