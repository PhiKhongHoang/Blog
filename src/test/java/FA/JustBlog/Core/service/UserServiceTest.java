package FA.JustBlog.Core.service;

import FA.JustBlog.Core.exception.AppException;
import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.models.dto.request.UserCreationRequest;
import FA.JustBlog.Core.models.dto.response.UserResponse;
import FA.JustBlog.Core.repositories.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @MockBean
    private IUserRepository userRepository;

    private UserCreationRequest request;
    private UserResponse userResponse;
    private LocalDate dob;
    private User user;

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

        user = User.builder()
                .id("bb9747a076c1")
                .username("tobii")
                .firstName("moc")
                .lastName("tieu")
                .dob(dob)
                .email("phik07tx02102002@gmail.com")
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        //GIVEN
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createUser(request);

        // THEN
        Assertions.assertThat(response.getId())
                .isEqualTo("bb9747a076c1");
        Assertions.assertThat(response.getUsername())
                .isEqualTo("tobii");


    }


    @Test
    void createUser_userExisted_fail() {
        //GIVEN
        when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);

        // WHEN
        var exception = assertThrows(AppException.class,
                () -> userService.createUser(request));


        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1003);

    }


}
