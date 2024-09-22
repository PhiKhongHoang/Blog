package FA.JustBlog.Core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1001, "key error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "user existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "username must be ad least {min} character", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "password must be ad least {min} character", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "user not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    EMAIL_INVALID(1009, "email not null", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTED(2002, "category existed", HttpStatus.BAD_REQUEST),
    TAG_EXISTED(3002, "tag existed", HttpStatus.BAD_REQUEST),
    POST_EXISTED(4002, "post existed", HttpStatus.BAD_REQUEST),
    COMMENT_EXISTED(5002, "comment existed", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatusCode statusCode;

}
