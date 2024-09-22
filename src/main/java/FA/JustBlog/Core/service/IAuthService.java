package FA.JustBlog.Core.service;

import java.text.ParseException;

import FA.JustBlog.Core.models.User;
import FA.JustBlog.Core.models.dto.request.LogoutRequest;
import FA.JustBlog.Core.models.dto.request.RefreshRequest;
import com.nimbusds.jose.JOSEException;

import FA.JustBlog.Core.models.dto.request.AuthenticationRequest;
import FA.JustBlog.Core.models.dto.request.IntrospectRequest;
import FA.JustBlog.Core.models.dto.response.AuthenticationResponse;
import FA.JustBlog.Core.models.dto.response.IntrospectResponse;

public interface IAuthService {
    public AuthenticationResponse authenticate(AuthenticationRequest request);

    public String generateToken(User user);

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    public void logout(LogoutRequest request) throws ParseException, JOSEException;

    public String buildScope(User user);

    public AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
