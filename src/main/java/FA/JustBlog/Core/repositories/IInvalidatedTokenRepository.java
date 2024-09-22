package FA.JustBlog.Core.repositories;

import FA.JustBlog.Core.models.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
