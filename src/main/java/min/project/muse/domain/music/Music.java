package min.project.muse.domain.music;

import jakarta.persistence.*;
import lombok.*;
import min.project.muse.domain.comment.Comment;
import min.project.muse.domain.likes.Likes;
import min.project.muse.domain.mood.Mood;
import min.project.muse.domain.user.User;
import min.project.muse.web.dto.music.UpdateMusicRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Main Entity
 *
 * Field (id, title, artist, link, tags, describe, thumbnail)
 */

@Entity
@Getter
@Setter
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

    @ManyToMany
    @JoinTable(
            name = "music_mood",
            joinColumns = @JoinColumn(name = "music_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_id")
    )
    @Column(name = "mood")
    private Set<Mood> moods;

    @Column(name = "details")
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "music", fetch = FetchType.LAZY)
    private List<Likes> likes;

    @OneToMany(mappedBy = "music", fetch = FetchType.LAZY)
    private List<Comment> comments;

    @Column
    private LocalDateTime createDate;

    @PrePersist
    public void createdDate() {
        this.createDate = LocalDateTime.now();
    }

    public void update(UpdateMusicRequest updateDto, String filename, Set<Mood> moods) {

        this.image = filename;
        this.title = updateDto.getTitle();
        this.artist = updateDto.getArtist();
        this.moods = moods;
        this.details = updateDto.getDetails();
    }

}
