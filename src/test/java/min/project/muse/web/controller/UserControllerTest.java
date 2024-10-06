package min.project.muse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import min.project.muse.domain.user.User;
import min.project.muse.domain.user.UserRepository;
import min.project.muse.web.dto.user.AddUserRequest;
import min.project.muse.web.dto.user.LoginRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    private AddUserRequest addUserRequest;

    @BeforeEach
    void beforeEach() {

        addUserRequest = AddUserRequest.builder()
                .username("asdf")
                .password("asdf")
                .email("asdf@asdf.com")
                .build();

    }

    @Test
    public void loginTest() throws Exception {
        final String url = "/login";
        final String requestBody = objectMapper.writeValueAsString(new LoginRequest("qwer", "qwer"));

        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .content(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );

        resultActions.andExpect(status().isOk());

    }

    @Test
    public void signUpTest() throws Exception {

        // API 요청 설정
        final String url = "/signup";

        final String requestBody = objectMapper.writeValueAsString(addUserRequest);

        ResultActions resultActions = mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(requestBody)
        );

        User user = userRepository.findByUsername(addUserRequest.getUsername()).get();

        Assertions.assertThat(user.getUsername()).isEqualTo(addUserRequest.getUsername());
        Assertions.assertThat(user.getEmail()).isEqualTo(addUserRequest.getEmail());


    }

}