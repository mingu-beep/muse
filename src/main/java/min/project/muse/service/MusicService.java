package min.project.muse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.music.MusicRepository;
import min.project.muse.domain.user.PrincipalDetails;
import min.project.muse.domain.user.User;
import min.project.muse.util.MultipartFileUtil;
import min.project.muse.util.ConvertUtil;
import min.project.muse.web.dto.music.AddMusicRequest;
import min.project.muse.web.dto.music.ShowMusicResponse;
import min.project.muse.web.dto.music.UpdateMusicRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@RequiredArgsConstructor // final 이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service // 빈으로 등록
public class MusicService {

    @Value("${file.path}")
    private String uploadPath;

    @Value("${search.type}")
    private String searchType;

    private final MusicRepository musicRepository;

    // 음악 추가 method
    public Music save(AddMusicRequest request, User user) {

        MultipartFile image = request.getImage();
        String filename = MultipartFileUtil.saveImage(uploadPath, image);

        return musicRepository.save(request.toEntity(filename, user));
    }

    // 음악 조회 method
    public List<Music> findAll() {
        return musicRepository.findAll();
    }

    public Music findById(long id) {
        return musicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    public List<ShowMusicResponse> findMusicList(PrincipalDetails principal) {

        long loginUserId = principal != null ? principal.getUserId() : -1;

        return ConvertUtil.convertToMusicDto(musicRepository.findAll(), loginUserId);
    }

    public List<ShowMusicResponse> findPopularMusicList(PrincipalDetails principal) {

        long loginUserId = principal != null ? principal.getUserId() : -1;

        return ConvertUtil.convertToMusicDto(musicRepository.selectPopular(), loginUserId);
    }

    // 음악 삭제 method
    public void deleteById(long id) {
        musicRepository.deleteById(id);
    }

    // 음악 수정 method
    @Transactional
    public Music update(long id, UpdateMusicRequest request) {
        Music music = musicRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        String filename = request.getImage().isEmpty() ? music.getImage() : MultipartFileUtil.saveImage(uploadPath, request.getImage());
        music.update(request, filename);

        return music;
    }

    // 음악 검색 method
    public Map<String, List<Music>> search(String type, String keyword) {

        String[] types = searchType.split(",");
        Map<String, List<Music>> res = new HashMap<>();

        if (type.equals("all") || type.equals("title"))
            res.put("title", musicRepository.findByTitleContaining(keyword));
        if (type.equals("all") || type.equals("artist"))
            res.put("artist", musicRepository.findByArtistContaining(keyword));
        if (type.equals("all") || type.equals("mood"))
            res.put("mood", musicRepository.findByMoodsContaining(keyword));

        return res;
    }
}
