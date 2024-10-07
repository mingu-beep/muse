package min.project.muse.domain.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface MusicRepository extends JpaRepository<Music, Long> {
}
