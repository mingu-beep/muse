package min.project.muse.service;

import lombok.RequiredArgsConstructor;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.music.MusicRepository;
import min.project.muse.web.dto.AddMusicRequest;
import min.project.muse.web.dto.UpdateMusicRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor // final 이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class MusicService {

    @Value("${file.path}")
    private String uploadPath;

    private final MusicRepository musicRepository;

    // 음악 추가 method
    public Music save(AddMusicRequest request) {

        MultipartFile image = request.getImage();

        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + image.getOriginalFilename();
        Path imageFilePath = Paths.get(uploadPath + filename);

        try {
            Files.write(imageFilePath, image.getBytes());
        } catch (IOException e) {
            //
        }

        return musicRepository.save(request.toEntity(filename));
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
