package FA.JustBlog.Core.service.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import FA.JustBlog.Core.repositories.IRoleRepository;
import FA.JustBlog.Core.utils.EmailUtil;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import FA.JustBlog.Core.exception.AppException;
import FA.JustBlog.Core.exception.ErrorCode;
import FA.JustBlog.Core.mapper.UserMapper;
import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.models.dto.request.UserCreationRequest;
import FA.JustBlog.Core.models.dto.request.UserUpdateRequest;
import FA.JustBlog.Core.models.dto.response.UserResponse;
import FA.JustBlog.Core.repositories.IUserRepository;
import FA.JustBlog.Core.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements IUserService {
    IUserRepository userRepository;
    UserMapper userMapper;
    IRoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    EmailUtil emailUtil;

    @Override
//    @PreAuthorize("hasRole('BLOG_OWNER')")
    public UserResponse createUser(UserCreationRequest request) {
        log.info("Service: create user");

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public UserResponse updateUser(String id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<UserResponse> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();

        if (!users.isEmpty()) {
            for (User user : users) {
                userResponses.add(userMapper.toUserResponse(user));
            }
        }

        return userResponses;
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public UserResponse getUser(String id) {
        return userMapper
                .toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }

    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public String forgotPassword(String email) {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        try {
            emailUtil.sendSetPasswordEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send set password email please try again");
        }
        return "Please check your email to set new password to your account";
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public String setPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return "New password set successfully login with new password";
    }
}
