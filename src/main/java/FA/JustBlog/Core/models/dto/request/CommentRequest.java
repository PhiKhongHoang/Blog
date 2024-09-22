package FA.JustBlog.Core.models.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentRequest {
    String name;
    String email;
    String commentHeader;
    String commentText;
    String postId;
}
