package FA.JustBlog.Core.mapper;

import FA.JustBlog.Core.models.Comment;
import FA.JustBlog.Core.models.dto.request.CommentRequest;
import FA.JustBlog.Core.models.dto.response.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    public Comment toComment(CommentRequest request);

    public void updateComment(@MappingTarget Comment comment, CommentRequest request);

    public CommentResponse toCommentResponse(Comment comment);
}
