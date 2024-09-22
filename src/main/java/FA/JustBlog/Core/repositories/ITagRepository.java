package FA.JustBlog.Core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import FA.JustBlog.Core.models.Tag;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ITagRepository extends JpaRepository<Tag, String> {
    public Optional<Tag> findByNameAndDescription(String name, String description);

    public Optional<Tag> findByUrlSlug(String urlSlug);

    @Query("SELECT t FROM Tag t JOIN t.posts p WHERE p.id = :id")
    public Set<Tag> findByPostId(@Param("id") String id);

    public Page<Tag> findAll(Pageable pageable);

    public boolean existsByName(String name);
}
