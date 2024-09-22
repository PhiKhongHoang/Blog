package FA.JustBlog.Core.controller;

import java.util.List;

import FA.JustBlog.Core.models.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import FA.JustBlog.Core.models.Post;
import FA.JustBlog.Core.models.dto.request.CommentRequest;
import FA.JustBlog.Core.models.dto.request.PostRequest;
import FA.JustBlog.Core.models.dto.response.CommentResponse;
import FA.JustBlog.Core.models.dto.response.PostResponse;
import FA.JustBlog.Core.service.ICommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> getComment(@PathVariable String id) {
        CommentResponse commentResponse = commentService.getComment(id);
        ApiResponse<CommentResponse> apiResponse = ApiResponse.<CommentResponse>builder()
                .result(commentResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentService.createComment(request);
        ApiResponse<CommentResponse> apiResponse = ApiResponse.<CommentResponse>builder()
                .result(commentResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/{postId}/{commentName}/{commentEmail}/{commentTitle}/{commentBody}")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@PathVariable String postId, @PathVariable String commentName,
                                                                      @PathVariable String commentEmail, @PathVariable String commentTitle,
                                                                      @PathVariable String commentBody) {
        CommentResponse commentResponse = commentService.createComment(postId, commentName, commentEmail, commentTitle, commentBody);
        ApiResponse<CommentResponse> apiResponse = ApiResponse.<CommentResponse>builder()
                .result(commentResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(@PathVariable String id, @RequestBody CommentRequest request) {
        CommentResponse commentResponse = commentService.updateComment(id, request);
        ApiResponse<CommentResponse> apiResponse = ApiResponse.<CommentResponse>builder()
                .result(commentResponse)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> deletePost(@RequestBody CommentRequest request) {
        commentService.deleteComment(request);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable String id) {
        commentService.deleteComment(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments() {
        List<CommentResponse> commentResponses = commentService.getComments();
        ApiResponse<List<CommentResponse>> apiResponse = ApiResponse.<List<CommentResponse>>builder()
                .result(commentResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/forpost/{id}")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentsForPost(@PathVariable String id) {
        List<CommentResponse> commentResponses = commentService.getCommentsForPost(id);
        ApiResponse<List<CommentResponse>> apiResponse = ApiResponse.<List<CommentResponse>>builder()
                .result(commentResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/forpost")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getCommentsForPost(@RequestBody Post post) {
        List<CommentResponse> commentResponses = commentService.getCommentsForPost(post);
        ApiResponse<List<CommentResponse>> apiResponse = ApiResponse.<List<CommentResponse>>builder()
                .result(commentResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

}
