package FA.JustBlog.Core.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Tags")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // uuid: chuỗi không bh trùng lặp
    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "url_slug")
    String urlSlug;

    @Column(name = "description")
    String description;

    @ManyToMany(mappedBy = "tags")
    Set<Post> posts;

}
