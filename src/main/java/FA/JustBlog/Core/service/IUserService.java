package FA.JustBlog.Core.service;

import java.util.List;

import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.models.dto.request.UserCreationRequest;
import FA.JustBlog.Core.models.dto.request.UserUpdateRequest;
import FA.JustBlog.Core.models.dto.response.UserResponse;

public interface IUserService {
    public UserResponse createUser(UserCreationRequest request);

    public List<UserResponse> getUsers();

    public UserResponse getUser(String id);

    public UserResponse updateUser(String id, UserUpdateRequest request);

    public void deleteUser(String id);

    public UserResponse getMyInfo();

    public String forgotPassword(String email);

    public String setPassword(String email, String newPassword);
}
