package FA.JustBlog.Core.controller;

import FA.JustBlog.Core.models.dto.request.LogoutRequest;
import FA.JustBlog.Core.models.dto.request.RefreshRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import FA.JustBlog.Core.models.dto.request.AuthenticationRequest;
import FA.JustBlog.Core.models.dto.request.IntrospectRequest;
import FA.JustBlog.Core.models.dto.response.AuthenticationResponse;
import FA.JustBlog.Core.models.dto.response.IntrospectResponse;
import FA.JustBlog.Core.models.response.ApiResponse;
import FA.JustBlog.Core.service.IAuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.text.ParseException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    IAuthService authService;

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws JOSEException, ParseException {
        var result = authService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = authService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws JOSEException, ParseException {

        authService.logout(request);

        return ApiResponse.<Void>builder()
                .build();
    }

}
