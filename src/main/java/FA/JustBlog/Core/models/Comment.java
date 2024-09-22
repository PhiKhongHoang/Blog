package FA.JustBlog.Core.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // uuid: chuỗi không bh trùng lặp
    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "email")
    String email;

    @Column(name = "comment_header")
    String commentHeader;

    @Column(name = "comment_text")
    String commentText;

    @Column(name = "comment_time")
    LocalDateTime commentTime;

    @ManyToOne
    @JoinColumn(name = "post_id")
    Post post;
}
