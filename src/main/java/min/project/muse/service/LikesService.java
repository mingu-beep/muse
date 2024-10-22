package min.project.muse.service;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.likes.LikesRepository;
import min.project.muse.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;

    @Transactional
    public void like(long musicId, User user) {
        likesRepository.insertLikes(musicId, user.getId());
    }

    @Transactional
    public void unlike(long musicId, User user) {
        likesRepository.deleteLikes(musicId, user.getId());
    }

}
