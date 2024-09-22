package FA.JustBlog.Core.service;

import java.util.List;

import FA.JustBlog.Core.models.dto.request.CategoryRequest;
import FA.JustBlog.Core.models.dto.response.CategoryResponse;

public interface ICategoryService {
    public CategoryResponse getCategory(String id);

    public CategoryResponse getCategoryByName(String name);

    public CategoryResponse createCategory(CategoryRequest request);

    public CategoryResponse updateCategory(String id, CategoryRequest request);

    public void deleteCategory(CategoryRequest request);

    public void deleteCategory(String categoryId);

    public List<CategoryResponse> getCtesgories();

    public List<CategoryResponse> getCategories(int page, int size);
}
