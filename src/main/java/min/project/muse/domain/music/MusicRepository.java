package min.project.muse.domain.music;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<Music> findByTitleContaining(String keyword);

    List<Music> findByArtistContaining(String keyword);

    List<Music> findByCreateDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT m.* FROM music m INNER JOIN (SELECT *, COUNT(*) likecount FROM likes GROUP BY music_id) l ON m.id = l.music_id ORDER BY l.likecount desc", nativeQuery = true)
    Page<Music> selectPopular(Pageable pageable);

    @Query(value = "SELECT * FROM music", nativeQuery = true)
    Page<Music> selectMusicList(Pageable pageable);

    @Query(value = "SELECT * FROM music WHERE create_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Page<Music> selectByCreateDateBetween(@Param("startDate") LocalDateTime startDate,
                                          @Param("endDate") LocalDateTime endDate, Pageable pageable);
//    @Query(value = "SELECT COUNT(*) FROM music WHERE mood LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
//    Integer countMusicsByMood(@Param("keyword") String keyword);

}
