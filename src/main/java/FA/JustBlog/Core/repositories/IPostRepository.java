package FA.JustBlog.Core.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FA.JustBlog.Core.models.Post;

import java.time.LocalDateTime;

import FA.JustBlog.Core.models.Category;

import java.util.Set;

import FA.JustBlog.Core.models.Tag;

@Repository
public interface IPostRepository extends JpaRepository<Post, String> {
    public List<Post> findByUrlSlug(String urlSlug);

    public Optional<Post> findByTitleAndShortDescriptionAndPostContent(String title, String shortDescription,
                                                                       String postContent);

    public List<Post> findByPosterOn(boolean posterOn);

    public List<Post> findByPublished(LocalDateTime published);

    public List<Post> findByCategory(Category category);

    public Page<Post> findAll(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.shortDescription LIKE %:keyword%")
    public List<Post> searchByTitleOrShortDescription(@Param("keyword") String keyword);

    public boolean existsByTitle(String title);
}
