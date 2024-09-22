package FA.JustBlog.Core.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    String id;
    String title;
    String shortDescription;
    String postContent;
    String urlSlug;
    LocalDateTime published;
    boolean posterOn;
    boolean modified;
    String categoryId;
    int viewCount;
    int rateCount;
    int totalRate;
    List<String> tagId;
}
