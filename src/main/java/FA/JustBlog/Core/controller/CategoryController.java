package FA.JustBlog.Core.controller;

import java.util.List;

import FA.JustBlog.Core.models.response.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import FA.JustBlog.Core.models.dto.request.CategoryRequest;
import FA.JustBlog.Core.models.dto.response.CategoryResponse;
import FA.JustBlog.Core.service.ICategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryController {
    ICategoryService categoryService;

    @GetMapping("/id/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategory(@PathVariable String id) {
        CategoryResponse categoryResponse = categoryService.getCategory(id);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryByName(@PathVariable String name) {
        CategoryResponse categoryResponse = categoryService.getCategoryByName(name);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable String id, @RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.updateCategory(id, request);
        ApiResponse<CategoryResponse> apiResponse = ApiResponse.<CategoryResponse>builder()
                .result(categoryResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<String>> deleteCategory(@RequestBody CategoryRequest request) {
        categoryService.deleteCategory(request);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .message("Delete is success")
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.getCtesgories();
        ApiResponse<List<CategoryResponse>> apiResponse = ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/page")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getTags(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        List<CategoryResponse> categoryResponses = categoryService.getCategories(page, size);
        ApiResponse<List<CategoryResponse>> apiResponse = ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryResponses)
                .build();

        return ResponseEntity.ok(apiResponse);
    }
}
