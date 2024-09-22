package FA.JustBlog.Core.models.dto.response;

import java.time.LocalDate;
import java.util.Set;

import FA.JustBlog.Core.models.Role;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    String email;
    LocalDate dob;
//    Set<RoleResponse> roles;
}
