package FA.JustBlog.Core.service.serviceImpl;

import FA.JustBlog.Core.mapper.RoleMapper;
import FA.JustBlog.Core.models.dto.request.RoleRequest;
import FA.JustBlog.Core.models.dto.response.RoleResponse;
import FA.JustBlog.Core.repositories.IPermissionRepository;
import FA.JustBlog.Core.repositories.IRoleRepository;
import FA.JustBlog.Core.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements IRoleService {
    IRoleRepository roleRepository;
    IPermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());

        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
