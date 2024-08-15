package min.project.muse.domain.music;

import jakarta.persistence.*;
import lombok.*;

/**
 * Main Entity
 *
 * Field (id, title, singer, link, tags, describe, thumbnail)
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

    @Column(name = "singer")
    private String singer;

    @Column(name = "mood")
    private String mood;

    @Column(name = "content")
    private String content;

    public void update(String content) {
        this.content = content;
    }

}
