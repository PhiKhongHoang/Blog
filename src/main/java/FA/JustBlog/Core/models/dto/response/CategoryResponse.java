package FA.JustBlog.Core.models.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapping;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryResponse {
    String id;
    String name;
    String urlSlug;
    String description;
}
