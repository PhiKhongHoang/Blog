package FA.JustBlog.Core.models.dto.request;

import java.time.LocalDate;
import java.util.List;

import FA.JustBlog.Core.validator.DobConstraint;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    //    @Size(min = 4, message = "USERNAME_INVALID")
    @Size(min = 4, message = "PASSWORD_INVALID")
    String username;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;

    @NotBlank(message = "EMAIL_INVALID")
    String email;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
