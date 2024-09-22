package FA.JustBlog.Core.service;

import FA.JustBlog.Core.models.dto.request.PermissionRequest;
import FA.JustBlog.Core.models.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    public void delete(String permission);

    public List<PermissionResponse> getAll();

    public PermissionResponse create(PermissionRequest request);
}
