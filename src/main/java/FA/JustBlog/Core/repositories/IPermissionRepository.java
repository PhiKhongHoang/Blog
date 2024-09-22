package FA.JustBlog.Core.repositories;

import FA.JustBlog.Core.models.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, String> {
}
