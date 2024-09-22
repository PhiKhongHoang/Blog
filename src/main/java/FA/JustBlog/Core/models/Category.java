package FA.JustBlog.Core.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // uuid: chuỗi không bh trùng lặp
    @Column(name = "id")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "url_Slug")
    String urlSlug;

    @Column(name = "description")
    String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    List<Post> posts;
}
