package min.project.muse.web.dto.user;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;

    public String getPassword(PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(this.password);
    }
}
