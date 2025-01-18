package min.project.muse.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.mood.Mood;
import min.project.muse.domain.mood.MoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final MoodRepository moodRepository;

    @Override
    public void run(String... args) throws Exception {

        if (moodRepository.count() == 0) {

            moodRepository.save(Mood.builder().label("joyful").script("행복").build());
            moodRepository.save(Mood.builder().label("melancholic").script("슬픔").build());
            moodRepository.save(Mood.builder().label("peaceful").script("평온").build());
            moodRepository.save(Mood.builder().label("romantic").script("로맨틱").build());
            moodRepository.save(Mood.builder().label("mysterious").script("신비").build());
            moodRepository.save(Mood.builder().label("energetic").script("에너제틱").build());
            moodRepository.save(Mood.builder().label("hopeful").script("희망").build());

            log.info(" Default Mood Setting Complete");

        }

    }
}
