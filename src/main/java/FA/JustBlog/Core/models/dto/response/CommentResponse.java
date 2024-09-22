package FA.JustBlog.Core.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String id;
    String name;
    String email;
    String commentHeader;
    String commentText;
    LocalDateTime commentTime;
    String postId;
}
