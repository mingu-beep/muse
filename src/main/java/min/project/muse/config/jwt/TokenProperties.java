package min.project.muse.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
public class TokenProperties {

    private final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    private final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    private final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

}
