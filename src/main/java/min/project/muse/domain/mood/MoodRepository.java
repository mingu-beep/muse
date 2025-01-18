package min.project.muse.domain.mood;

import org.springframework.data.jpa.repository.JpaRepository;


public interface MoodRepository extends JpaRepository<Mood, Long> {

    Mood findByLabel(String label);

    Mood findByScript(String script);

}
