package FA.JustBlog.Core.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FA.JustBlog.Core.models.Comment;

import java.util.List;
import java.util.Optional;

import FA.JustBlog.Core.models.Post;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, String> {
    public Optional<Comment> findByNameAndEmailAndCommentHeaderAndCommentText(String name, String email,
                                                                              String commentHeader, String commentText);

    public List<Comment> findByPost(Post post);

    public Page<Comment> findAll(Pageable pageable);

    public boolean existsByName(String name);
}
