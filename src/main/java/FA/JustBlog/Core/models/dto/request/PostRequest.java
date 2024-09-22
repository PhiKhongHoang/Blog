package FA.JustBlog.Core.models.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {
    String title;
    String shortDescription;
    String postContent;
    String categoryId;
    List<String> tagId;
}
