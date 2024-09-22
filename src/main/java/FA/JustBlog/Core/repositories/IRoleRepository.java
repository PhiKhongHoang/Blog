package FA.JustBlog.Core.repositories;

import FA.JustBlog.Core.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, String> {
}
