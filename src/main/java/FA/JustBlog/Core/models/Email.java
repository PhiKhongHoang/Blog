package FA.JustBlog.Core.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Email {
    String to;
    String subject;
    String text;
    MultipartFile attachment;
}
