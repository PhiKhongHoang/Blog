package FA.JustBlog.Core.controller;

import FA.JustBlog.Core.models.dto.request.UserCreationRequest;
import FA.JustBlog.Core.models.dto.response.UserResponse;
import FA.JustBlog.Core.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2001, 1, 1);
        List<String> roles = new ArrayList<>();
        roles.add("USER");

        request = UserCreationRequest.builder()
                .username("tobii")
                .password("12345678")
                .firstName("moc")
                .lastName("tieu")
                .dob(dob)
                .email("phik07tx02102002@gmail.com")
                .roles(roles)
                .build();

        userResponse = UserResponse.builder()
                .id("bb9747a076c1")
                .username("tobii")
                .firstName("moc")
                .lastName("tieu")
                .dob(dob)
                .email("phik07tx02102002@gmail.com")
//                .roles()
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                .thenReturn(userResponse);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("code")
                        .value(0))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("result.id")
                        .value("bb9747a076c1")
                );

    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {
        // GIVEN
        request.setUsername("use");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .isBadRequest())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("code")
                        .value(1003))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("message")
                        .value("username must be ad least 4 characte")
                );

    }
}
