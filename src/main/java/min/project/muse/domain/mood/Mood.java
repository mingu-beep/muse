package min.project.muse.domain.mood;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import min.project.muse.domain.music.Music;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String label;

    @NotBlank
    private String script;

    @ManyToMany(mappedBy = "moods")
    private Set<Music> musics;

}
