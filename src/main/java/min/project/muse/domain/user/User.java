package min.project.muse.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import min.project.muse.domain.likes.Likes;
import min.project.muse.domain.music.Music;
import min.project.muse.web.dto.user.UpdateUserProfileRequest;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "bio")
    private String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Music> musicList;

    public User update(String profileImage, UpdateUserProfileRequest dto) {

        this.profileImage = profileImage;
        this.nickname = dto.getNickname();
        this.email = dto.getEmail();
        this.bio = dto.getBio();

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}