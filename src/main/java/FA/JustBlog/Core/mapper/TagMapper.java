package FA.JustBlog.Core.mapper;

import FA.JustBlog.Core.models.Tag;
import FA.JustBlog.Core.models.dto.request.TagRequest;
import FA.JustBlog.Core.models.dto.response.TagResponse;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    public Tag toTag(TagRequest request);

    public void updateTag(@MappingTarget Tag tag, TagRequest request);

    public TagResponse toTagResponse(Tag tag);
}
