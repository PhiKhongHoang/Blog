package FA.JustBlog.Core.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;


import FA.JustBlog.Core.exception.AppException;
import FA.JustBlog.Core.exception.ErrorCode;
import FA.JustBlog.Core.mapper.CategoryMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import FA.JustBlog.Core.models.Category;
import FA.JustBlog.Core.models.dto.request.CategoryRequest;
import FA.JustBlog.Core.models.dto.response.CategoryResponse;
import FA.JustBlog.Core.repositories.ICategoryRepository;
import FA.JustBlog.Core.service.ICategoryService;
import FA.JustBlog.Core.utils.SlugUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryServiceImpl implements ICategoryService {
    ICategoryRepository categoryRepository;
    CategoryMapper categoryMapper;


    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public CategoryResponse getCategory(String id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found")));
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }

        Category category = categoryMapper.toCategory(request);
        category.setUrlSlug(SlugUtils.toSlug(request.getName()));

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public CategoryResponse updateCategory(String id, CategoryRequest request) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));

        categoryMapper.updateCategory(category, request);
        category.setUrlSlug(SlugUtils.toSlug(request.getName()));

        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deleteCategory(CategoryRequest request) {
        Category result = categoryRepository.findByNameAndDescription(request.getName(), request.getDescription())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(result);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<CategoryResponse> getCtesgories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        if (!categories.isEmpty()) {
            for (Category category : categories) {
                categoryResponses.add(categoryMapper.toCategoryResponse(category));
            }
        }
        return categoryResponses;
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public CategoryResponse getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name).orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<CategoryResponse> getCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categpryPage = categoryRepository.findAll(pageable);

        List<CategoryResponse> categoryResponses = new ArrayList<>();
        if (categpryPage.hasContent()) {
            for (Category category : categpryPage) {
                categoryResponses.add(categoryMapper.toCategoryResponse(category));
            }
        }
        return categoryResponses;
    }

}
