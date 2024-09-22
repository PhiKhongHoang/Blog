package FA.JustBlog.Core.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Posts")
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // uuid: chuỗi không bh trùng lặp
    @Column(name = "id")
    String id;

    @Column(name = "title")
    String title;

    @Column(name = "short_description")
    String shortDescription;

    @Column(name = "post_content", columnDefinition = "TEXT")
    @Lob
    String postContent;

    @Column(name = "url_Slug")
    String urlSlug;

    @Column(name = "published")
    LocalDateTime published;

    @Column(name = "poster_on")
    boolean posterOn;

    @Column(name = "modified")
    boolean modified;

    @Column(name = "viewCount")
    int viewCount;

    @Column(name = "rateCount")
    int rateCount;

    @Column(name = "totalRate")
    int totalRate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToMany
    @JoinTable(name = "PostTagMap", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<Tag> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    List<Comment> comments;
}
