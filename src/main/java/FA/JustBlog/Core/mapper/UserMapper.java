package FA.JustBlog.Core.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.models.dto.request.UserCreationRequest;
import FA.JustBlog.Core.models.dto.request.UserUpdateRequest;
import FA.JustBlog.Core.models.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    public User toUser(UserCreationRequest request);

    // map từ request vào user
    @Mapping(target = "roles", ignore = true)
    public void updateUser(@MappingTarget User user, UserUpdateRequest request);

    // @Mapping(source = "firstName", target = "lastName") // source: nguồn, target:
    // đích
    // @Mapping(target = "lastName", ignore = true) // bỏ qua trường lastName không map
    public UserResponse toUserResponse(User user);
}
