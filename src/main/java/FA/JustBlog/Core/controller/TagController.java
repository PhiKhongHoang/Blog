package FA.JustBlog.Core.controller;

import java.util.List;


import FA.JustBlog.Core.models.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import FA.JustBlog.Core.models.dto.request.TagRequest;
import FA.JustBlog.Core.models.dto.response.TagResponse;
import FA.JustBlog.Core.service.ITagService;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagController {
    ITagService tagService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> getTag(@PathVariable String id) {
        TagResponse tagResponse = tagService.getTag(id);
        ApiResponse<TagResponse> apiResponse = ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<TagResponse>> createTag(@RequestBody TagRequest request) {
        TagResponse tagResponse = tagService.createTag(request);
        ApiResponse<TagResponse> apiResponse = ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> UpdateTag(@PathVariable String id, @RequestBody TagRequest request) {
        TagResponse tagResponse = tagService.updateTag(id, request);
        ApiResponse<TagResponse> apiResponse = ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> DeleteTag(@RequestBody TagRequest request) {
        tagService.deleteTag(request);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable String id) {
        tagService.deleteTag(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<TagResponse>>> getTags() {
        List<TagResponse> tagResponses = tagService.getTags();
        ApiResponse<List<TagResponse>> apiResponse = ApiResponse.<List<TagResponse>>builder()
                .result(tagResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/urlslug/{urlSlug}")
    public ResponseEntity<ApiResponse<TagResponse>> GetTagByUrlSlug(@PathVariable String urlSlug) {
        TagResponse tagResponse = tagService.getTagByUrlSlug(urlSlug);
        ApiResponse<TagResponse> apiResponse = ApiResponse.<TagResponse>builder()
                .result(tagResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<List<TagResponse>>> getTags(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                  @RequestParam(value = "size", defaultValue = "2") int size) {
        List<TagResponse> tagResponses = tagService.getTags(page, size);
        ApiResponse<List<TagResponse>> apiResponse = ApiResponse.<List<TagResponse>>builder()
                .result(tagResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
