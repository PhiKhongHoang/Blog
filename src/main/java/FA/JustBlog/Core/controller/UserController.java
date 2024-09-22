package FA.JustBlog.Core.controller;

import java.util.List;

import FA.JustBlog.Core.models.dto.response.CategoryResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.models.dto.request.UserCreationRequest;
import FA.JustBlog.Core.models.dto.request.UserUpdateRequest;
import FA.JustBlog.Core.models.dto.response.UserResponse;
import FA.JustBlog.Core.models.response.ApiResponse;
import FA.JustBlog.Core.service.IUserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    @PostMapping()
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller: create user");

        UserResponse userResponse = userService.createUser(request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsers() {
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username: {}", authentication.getName());
//        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));


        List<UserResponse> users = userService.getUsers();

        ApiResponse<List<UserResponse>> apiResponse = ApiResponse.<List<UserResponse>>builder()
                .result(users)
                .build();

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable String id) {
        UserResponse userResponse = userService.getUser(id);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/myInfo")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
        UserResponse userResponse = userService.getMyInfo();
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String id, @RequestBody @Valid UserUpdateRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder()
                .result(userResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/set-password")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestHeader String newPassword) {
        return new ResponseEntity<>(userService.setPassword(email, newPassword), HttpStatus.OK);
    }

}
