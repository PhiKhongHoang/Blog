package FA.JustBlog.Core.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FA.JustBlog.Core.models.Category;

import java.util.List;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, String> {
    public boolean existsByName(String name);

    public Optional<Category> findByNameAndDescription(String name, String description);

    public Optional<Category> findByName(String name);

    public Optional<Category> findByUrlSlug(String urlSlug);

    public Page<Category> findAll(Pageable pageable);
}
