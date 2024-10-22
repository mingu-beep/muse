package min.project.muse.domain.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(music_id, user_id, create_date) VALUES (:musicId, :userId, now())", nativeQuery = true)
    void insertLikes(@Param("musicId") long musicId, @Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE (music_id = :musicId AND user_id = :userId)", nativeQuery = true)
    void deleteLikes(@Param("musicId") long musicId, @Param("userId") long userId);
}
