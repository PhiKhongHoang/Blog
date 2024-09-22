package FA.JustBlog.Core.controller;

import java.sql.Date;
import java.util.List;

import FA.JustBlog.Core.models.response.ApiResponse;
import FA.JustBlog.Core.service.IPostService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import FA.JustBlog.Core.models.dto.request.PostRequest;
import FA.JustBlog.Core.models.dto.response.PostResponse;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    IPostService postService;

    @PostMapping()
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody PostRequest request) {
        PostResponse postResponse = postService.createPost(request);
        ApiResponse<PostResponse> apiResponse = ApiResponse.<PostResponse>builder()
                .result(postResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable String id) {
        PostResponse postResponse = postService.getPost(id);
        ApiResponse<PostResponse> apiResponse = ApiResponse.<PostResponse>builder()
                .result(postResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{year}/{month}/{urlSlug}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable int year, @PathVariable int month,
                                                             @PathVariable String urlSlug) {
        PostResponse postResponse = postService.getPost(year, month, urlSlug);
        ApiResponse<PostResponse> apiResponse = ApiResponse.<PostResponse>builder()
                .result(postResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable String id, @RequestBody PostRequest request) {
        PostResponse postResponse = postService.updatePost(id, request);
        ApiResponse<PostResponse> apiResponse = ApiResponse.<PostResponse>builder()
                .result(postResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> deletePost(@RequestBody PostRequest request) {
        postService.deletePost(request);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable String id) {
        postService.deletePost(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts() {
        List<PostResponse> postResponses = postService.getPosts();
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/publised")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPublisedPosts() {
        List<PostResponse> postResponses = postService.getPublisedPosts();
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/unpublised")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getUnpublisedPosts() {
        List<PostResponse> postResponses = postService.getUnpublisedPosts();
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/lasest/{size}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getLatestPost(@PathVariable int size) {
        List<PostResponse> postResponses = postService.getLatestPost(size);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/bymonth/{date}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostsByMonth(@PathVariable Date date) {
        List<PostResponse> postResponses = postService.getPostsByMonth(date);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/count_category_name/{category}")
    public ResponseEntity<ApiResponse<Integer>> countPostsForCategory(@PathVariable String category) {
        ApiResponse<Integer> apiResponse = ApiResponse.<Integer>builder()
                .result(Integer.valueOf(postService.countPostsForCategory(category)))
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/category_name/{category}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostsByCategory(@PathVariable String category) {
        List<PostResponse> postResponses = postService.getPostsByCategory(category);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/view/{size}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getMostViewedPost(@PathVariable int size) {
        List<PostResponse> postResponses = postService.getMostViewedPost(size);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/rate/{size}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getHighestPosts(@PathVariable int size) {
        List<PostResponse> postResponses = postService.getHighestPosts(size);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/Category/{category}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostsByCategoryUrlSlug(@PathVariable String category) {
        List<PostResponse> postResponses = postService.getPostsByCategoryUrlSlug(category);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getTags(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                   @RequestParam(value = "size", defaultValue = "2") int size) {
        List<PostResponse> postResponses = postService.getPosts(page, size);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<PostResponse>>> searchPosts(@RequestParam String keyword) {
        List<PostResponse> postResponses = postService.searchPosts(keyword);
        ApiResponse<List<PostResponse>> apiResponse = ApiResponse.<List<PostResponse>>builder()
                .result(postResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
