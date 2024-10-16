package min.project.muse.web.dto.music;

import lombok.*;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.User;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddMusicRequest {

    private String writer;
    private String title;
    private String artist;
    private MultipartFile image;
    private String moods;

    private String details;

    public Music toEntity(String imageUrl, User user) {
        return Music.builder()
                .user(user)
                .title(title)
                .artist(artist)
                .moods(moods)
                .details(details)
                .image(imageUrl)
                .build();
    }
}
