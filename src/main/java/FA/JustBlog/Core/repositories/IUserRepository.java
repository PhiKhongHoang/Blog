package FA.JustBlog.Core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FA.JustBlog.Core.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    public boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
