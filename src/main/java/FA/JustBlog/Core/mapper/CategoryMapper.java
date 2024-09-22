package FA.JustBlog.Core.mapper;

import FA.JustBlog.Core.models.Category;
import FA.JustBlog.Core.models.dto.request.CategoryRequest;
import FA.JustBlog.Core.models.dto.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    public Category toCategory(CategoryRequest request);

    public void updateCategory(@MappingTarget Category category, CategoryRequest request);

    public CategoryResponse toCategoryResponse(Category category);
}
