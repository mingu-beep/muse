package min.project.muse.domain.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findByTitleContaining(String keyword);

    List<Music> findByArtistContaining(String keyword);

    List<Music> findByMoodsContaining(String keyword);

    @Query(value = "SELECT m.* FROM music m INNER JOIN (SELECT *, COUNT(*) likecount FROM likes GROUP BY music_id) l ON m.id = l.music_id ORDER BY l.likecount desc", nativeQuery = true)
    List<Music> selectPopular();
}
