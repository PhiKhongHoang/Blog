package FA.JustBlog.Core.mapper;

import FA.JustBlog.Core.models.Permission;
import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.models.dto.request.PermissionRequest;
import FA.JustBlog.Core.models.dto.request.UserCreationRequest;
import FA.JustBlog.Core.models.dto.request.UserUpdateRequest;
import FA.JustBlog.Core.models.dto.response.PermissionResponse;
import FA.JustBlog.Core.models.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    public Permission toPermission(PermissionRequest request);

    // map từ request vào user
    public void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

    // @Mapping(source = "firstName", target = "lastName") // source: nguồn, target:
    // đích
    // @Mapping(target = "lastName", ignore = true) // bỏ qua trường lastName không map
    public PermissionResponse toPermissionResponse(Permission permission);
}
