package min.project.muse.domain.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findByTitleContaining(String keyword);

    List<Music> findByArtistContaining(String keyword);

    List<Music> findByMoodsContaining(String keyword);
}
