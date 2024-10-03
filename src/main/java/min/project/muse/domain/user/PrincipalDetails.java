package min.project.muse.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
public class PrincipalDetails  implements UserDetails { // UserDetails 를 상속받아 인증 객체로 사용

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().getKey();
            }
        });
        return null;
    }
    // 사용자의 id를 반환 (고유한 값)
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // 계정 만료 여부 판단
    @Override
    public boolean isAccountNonExpired() {
        return true; // 인증이 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true; // 잠금되지 않음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 만료되지 않음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true; // 사용 가능
    }
}