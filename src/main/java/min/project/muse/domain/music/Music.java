package min.project.muse.domain.music;

import jakarta.persistence.*;
import lombok.*;
import min.project.muse.domain.user.User;
import min.project.muse.web.dto.music.UpdateMusicRequest;

/**
 * Main Entity
 *
 * Field (id, title, artist, link, tags, describe, thumbnail)
 */

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder // AllArgsConstructor 가 필요하다.
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "image")
    private String image;

    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

//    @Convert(converter = StringListConverter.class)
    @Column(name = "mood")
    private String moods;

    @Column(name = "details")
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void update(UpdateMusicRequest updateDto, String filename) {
        this.image = filename;
        this.title = updateDto.getTitle();
        this.artist = updateDto.getArtist();
        this.moods = updateDto.getMoods();
        this.details = updateDto.getDetails();
    }

}
