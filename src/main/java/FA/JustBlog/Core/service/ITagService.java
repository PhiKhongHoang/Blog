package FA.JustBlog.Core.service;

import java.util.List;

import FA.JustBlog.Core.models.dto.request.TagRequest;
import FA.JustBlog.Core.models.dto.response.TagResponse;

public interface ITagService {
    public TagResponse getTag(String id);

    public TagResponse createTag(TagRequest request);

    public TagResponse updateTag(String id, TagRequest request);

    public void deleteTag(TagRequest request);

    public void deleteTag(String id);

    public List<TagResponse> getTags();

    public TagResponse getTagByUrlSlug(String urlSlug);

    public List<TagResponse> getTags(int page, int size);
}
