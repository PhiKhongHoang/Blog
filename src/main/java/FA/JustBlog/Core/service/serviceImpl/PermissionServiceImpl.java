package FA.JustBlog.Core.service.serviceImpl;

import FA.JustBlog.Core.mapper.PermissionMapper;
import FA.JustBlog.Core.models.Permission;
import FA.JustBlog.Core.models.dto.request.PermissionRequest;
import FA.JustBlog.Core.models.dto.response.PermissionResponse;
import FA.JustBlog.Core.repositories.IPermissionRepository;
import FA.JustBlog.Core.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements IPermissionService {
    IPermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
