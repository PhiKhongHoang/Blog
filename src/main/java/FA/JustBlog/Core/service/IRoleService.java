package FA.JustBlog.Core.service;

import FA.JustBlog.Core.models.dto.request.RoleRequest;
import FA.JustBlog.Core.models.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    public void delete(String role);

    public List<RoleResponse> getAll();

    public RoleResponse create(RoleRequest request);
}
