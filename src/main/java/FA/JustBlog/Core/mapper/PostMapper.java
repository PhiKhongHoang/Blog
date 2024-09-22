package FA.JustBlog.Core.mapper;

import FA.JustBlog.Core.models.Post;
import FA.JustBlog.Core.models.dto.request.PostRequest;
import FA.JustBlog.Core.models.dto.response.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    public Post toPost(PostRequest request);

    public void updatePost(@MappingTarget Post post, PostRequest request);

    public PostResponse toPostResponse(Post post);
}
