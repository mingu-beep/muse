package min.project.muse.domain.music;

import jakarta.persistence.*;
import lombok.*;
import min.project.muse.domain.user.User;
import min.project.muse.util.StringListConverter;

import java.util.List;

/**
 * Main Entity
 *
 * Field (id, title, artist, link, tags, describe, thumbnail)
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Convert(converter = StringListConverter.class)
    @Column(name = "mood")
    private List<String> mood;

    @Column(name = "details")
    private String details;

//    @Column(name = "writer", nullable = false)
//    private String writer;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public void update(String details) {
        this.details = details;
    }

}
