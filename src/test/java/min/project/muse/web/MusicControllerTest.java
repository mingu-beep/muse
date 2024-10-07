package min.project.muse.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import min.project.muse.domain.music.Music;
import min.project.muse.domain.music.MusicRepository;
import min.project.muse.web.dto.music.AddMusicRequest;
import min.project.muse.web.dto.music.UpdateMusicRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc // MockMvc 생성 및 자동 구성
class MusicControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    MusicRepository musicRepository;

    @BeforeEach
    public void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        musicRepository.deleteAll();
    }

    @DisplayName("addMusic: 음악 추가에 성공한다.")
    @Test
    public void addMusic() throws Exception {

        // given
        final String url = "/api/musics";

        final String title = "title";
        final String artist = "artist";
        final String details = "details";
        final List<String> mood = List.of("a", "b", "c");
        
        final AddMusicRequest musicRequest = AddMusicRequest.builder()
                .title(title)
                .artist(artist)
                .details(details)
                .mood(mood)
                .build();

        final String requestBody = objectMapper.writeValueAsString(musicRequest);

        // when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        // then
        result.andExpect(status().isCreated());

        List<Music> musics = musicRepository.findAll();

        assertThat(musics.size()).isEqualTo(1);
        assertThat(musics.get(0).getTitle()).isEqualTo(title);
        assertThat(musics.get(0).getDetails()).isEqualTo(details);

    }

    @DisplayName("findAllMusics: 음악 목록 조회에 성공한다.")
    @Test
    public void findAllMusics() throws Exception {
        // given
        final String url = "/api/musics";
        final String title = "title";
        final String singer = "singer";

        musicRepository.save(Music.builder().title(title).artist(singer).build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].singer").value(singer))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findMusic: 음악 조회에 성공한다.")
    @Test
    public void findMusic() throws Exception {
        // given
        final String url = "/api/musics/{id}";
        final String title = "title";
        final String singer = "singer";

        Music saved = musicRepository.save(Music.builder().title(title).artist(singer).build());

        // when
        final ResultActions resultActions = mockMvc.perform(get(url, saved.getId()));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.singer").value(singer))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deleteMusic: 음악 삭제에 성공한다.")
    @Test
    public void deleteMusic() throws Exception {

        //given
        final String url = "/api/musics/{id}";
        final String title = "title";
        final String singer = "singer";

        Music savedMusic = musicRepository.save(Music.builder().title(title).artist(singer).build());

        // when
        mockMvc.perform(delete(url, savedMusic.getId()))
                .andExpect(status().isOk());

        // then
        List<Music> musics = musicRepository.findAll();

        assertThat(musics).isEmpty();

    }

//    @DisplayName("updateMusic: 음악 수정에 성공한다.")
//    @Test
//    public void updateMusic() throws Exception {
//
//        // given
//        final String url = "/api/musics/{id}";
//        final String title = "title";
//        final String singer = "singer";
//        final String content = "content";
//
//        Music savedMusic = musicRepository.save(Music.builder().title(title).artist(singer).details(content).build());
//
//        final String newContent = "new_content";
//
//        UpdateMusicRequest request = new UpdateMusicRequest(newContent);
//
//        // when
//        ResultActions result = mockMvc.perform(put(url, savedMusic.getId())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(request)));
//
//        // then
//        result.andExpect(status().isOk());
//
//        Music music = musicRepository.findById(savedMusic.getId()).get();
//
//        assertThat(music.getDetails()).isEqualTo(newContent);
//    }

}