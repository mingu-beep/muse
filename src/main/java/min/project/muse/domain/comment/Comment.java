package min.project.muse.domain.comment;

import jakarta.persistence.*;
import lombok.*;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.user.User;

import java.time.LocalDateTime;

@Table(name = "comments")
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column
    private String content;

    @Column
    private LocalDateTime createDate;

    @JoinColumn(name="user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "music_id")
    @ManyToOne
    private Music music;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
}
