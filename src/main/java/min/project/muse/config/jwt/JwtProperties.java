package min.project.muse.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("jwt") // 자바 클래스에 프로피티 값을 가져와서 사용하는 애너테이션
public class JwtProperties {

    private String issuer; // jwt.issuer 매핑
    private String secretKey; // jwt.secret_key 매핑
}
