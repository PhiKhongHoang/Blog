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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank(message = "password không được để trống")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;

    @NotBlank(message = "email không được để trống")
    String email;

    @DobConstraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    List<String> roles;
}
