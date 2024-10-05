package min.project.muse.domain.user;

import jakarta.persistence.*;
import lombok.*;
import min.project.muse.domain.music.Music;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Builder
@ToString
public class User { // UserDetails 를 상속받아 인증 객체로 사용

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

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Music> musicList;

    public User update(String email) {
        this.email = email;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}