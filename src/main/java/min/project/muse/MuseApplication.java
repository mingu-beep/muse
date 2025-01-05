package min.project.muse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MuseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuseApplication.class, args);
	}

}
