package FA.JustBlog.Core.service.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import FA.JustBlog.Core.exception.AppException;
import FA.JustBlog.Core.exception.ErrorCode;
import FA.JustBlog.Core.mapper.CommentMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import FA.JustBlog.Core.models.Comment;
import FA.JustBlog.Core.models.Post;
import FA.JustBlog.Core.models.dto.request.CommentRequest;
import FA.JustBlog.Core.models.dto.response.CommentResponse;
import FA.JustBlog.Core.repositories.ICommentRepository;
import FA.JustBlog.Core.repositories.IPostRepository;
import FA.JustBlog.Core.service.ICommentService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements ICommentService {
    ICommentRepository commentRepository;
    IPostRepository postRepository;
    CommentMapper commentMapper;

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public CommentResponse getComment(String id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        CommentResponse commentResponse = commentMapper.toCommentResponse(comment);
        commentResponse.setPostId(comment.getPost().getId());

        return commentResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public CommentResponse createComment(CommentRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (commentRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.COMMENT_EXISTED);
        }

        Comment comment = Comment.builder()
                .name(request.getName())
                .email(request.getEmail())
                .commentHeader(request.getCommentHeader())
                .commentText(request.getCommentText())
                .commentTime(LocalDateTime.now())
                .post(post)
                .build();

        commentRepository.save(comment);

        CommentResponse commentResponse = commentMapper.toCommentResponse(comment);
        commentResponse.setPostId(comment.getPost().getId());

        return commentResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public CommentResponse createComment(String id, String commentName, String commentEmail, String commentTitle,
                                         String commentBody) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (commentRepository.existsByName(commentName)) {
            throw new AppException(ErrorCode.COMMENT_EXISTED);
        }

        Comment comment = Comment.builder()
                .name(commentName)
                .email(commentEmail)
                .commentHeader(commentTitle)
                .commentText(commentBody)
                .commentTime(LocalDateTime.now())
                .post(post)
                .build();

        commentRepository.save(comment);

        CommentResponse commentResponse = commentMapper.toCommentResponse(comment);
        commentResponse.setPostId(id);

        return commentResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public CommentResponse updateComment(String id, CommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        comment.builder()
                .name(request.getName())
                .email(request.getEmail())
                .commentHeader(request.getCommentHeader())
                .commentText(request.getCommentText())
                .build();

        commentRepository.save(comment);

        CommentResponse commentResponse = commentMapper.toCommentResponse(comment);
        commentResponse.setPostId(id);

        return commentResponse;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deleteComment(CommentRequest request) {
        Comment comment = commentRepository
                .findByNameAndEmailAndCommentHeaderAndCommentText(
                        request.getName(), request.getEmail(), request.getCommentHeader(), request.getCommentText()
                ).orElseThrow(() -> new RuntimeException("Comment not found"));
        commentRepository.delete(comment);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deleteComment(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<CommentResponse> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponse> commentResponses = new ArrayList<>();
        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                commentResponses.add(commentMapper.toCommentResponse(comment));
            }
            for (int i = 0; i < comments.size(); i++) {
                commentResponses.get(i).setPostId(comments.get(i).getId());
            }
        }
        return commentResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<CommentResponse> getCommentsForPost(String id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));


        List<Comment> comments = commentRepository.findByPost(post);
        List<CommentResponse> commentResponses = new ArrayList<>();

        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                commentResponses.add(commentMapper.toCommentResponse(comment));
            }
            for (int i = 0; i < comments.size(); i++) {
                commentResponses.get(i).setPostId(comments.get(i).getPost().getId());
            }
        }
        return commentResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<CommentResponse> getCommentsForPost(Post post) {
        List<Comment> comments = commentRepository.findByPost(post);
        List<CommentResponse> commentResponses = new ArrayList<>();

        if (!comments.isEmpty()) {
            for (Comment comment : comments) {
                commentResponses.add(commentMapper.toCommentResponse(comment));
            }
            for (int i = 0; i < comments.size(); i++) {
                commentResponses.get(i).setPostId(post.getId());
            }
        }
        return commentResponses;
    }

}
