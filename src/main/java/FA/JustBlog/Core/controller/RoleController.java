package FA.JustBlog.Core.controller;

import FA.JustBlog.Core.models.dto.request.PermissionRequest;
import FA.JustBlog.Core.models.dto.request.RoleRequest;
import FA.JustBlog.Core.models.dto.response.PermissionResponse;
import FA.JustBlog.Core.models.dto.response.RoleResponse;
import FA.JustBlog.Core.models.response.ApiResponse;
import FA.JustBlog.Core.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    IRoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> delete(@PathVariable String id) {
        roleService.delete(id);
        return ApiResponse.<Void>builder().build();
    }
}
