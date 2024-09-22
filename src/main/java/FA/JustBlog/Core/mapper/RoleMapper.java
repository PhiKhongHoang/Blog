package FA.JustBlog.Core.mapper;

import FA.JustBlog.Core.models.Role;
import FA.JustBlog.Core.models.dto.request.RoleRequest;
import FA.JustBlog.Core.models.dto.response.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    public Role toRole(RoleRequest request);

    public RoleResponse toRoleResponse(Role role);
}
