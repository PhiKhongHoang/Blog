package FA.JustBlog.Core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import FA.JustBlog.Core.models.Post;
import FA.JustBlog.Core.models.dto.request.CommentRequest;
import FA.JustBlog.Core.models.dto.response.CommentResponse;

public interface ICommentService {
    public CommentResponse getComment(String id);

    public CommentResponse createComment(CommentRequest request);

    public CommentResponse createComment(String id, String commentName, String commentEmail, String commentTitle,
                                         String commentBody);

    public CommentResponse updateComment(String id, CommentRequest request);

    public void deleteComment(CommentRequest request);

    public void deleteComment(String id);

    public List<CommentResponse> getComments();

    public List<CommentResponse> getCommentsForPost(String id);

    public List<CommentResponse> getCommentsForPost(Post post);
}
