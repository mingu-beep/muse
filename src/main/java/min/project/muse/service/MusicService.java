package min.project.muse.service;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.music.MusicRepository;
import min.project.muse.web.dto.AddMusicRequest;
import min.project.muse.web.dto.UpdateMusicRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final 이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class MusicService {

    private final MusicRepository musicRepository;

    // 음악 추가 method
    public Music save(AddMusicRequest request) {
        return musicRepository.save(request.toEntity());
    }

    // 음악 조회 method
    public List<Music> findAll() {
        return musicRepository.findAll();
    }

    public Music findById(long id) {
        return musicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    // 음악 삭제 method
    public void deleteById(long id) {
        musicRepository.deleteById(id);
    }

    // 음악 수정 method
    @Transactional
    public Music update(long id, UpdateMusicRequest request) {
        Music music = musicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        music.update(request.getDetails());

        return music;
    }
}
