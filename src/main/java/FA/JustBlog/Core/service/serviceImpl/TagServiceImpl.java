package FA.JustBlog.Core.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import FA.JustBlog.Core.exception.AppException;
import FA.JustBlog.Core.exception.ErrorCode;
import FA.JustBlog.Core.mapper.TagMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import FA.JustBlog.Core.models.Tag;
import FA.JustBlog.Core.models.dto.request.TagRequest;
import FA.JustBlog.Core.models.dto.response.TagResponse;
import FA.JustBlog.Core.repositories.ITagRepository;
import FA.JustBlog.Core.service.ITagService;
import FA.JustBlog.Core.utils.SlugUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagServiceImpl implements ITagService {
    ITagRepository tagRepository;
    TagMapper tagMapper;

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public TagResponse getTag(String id) {
        return tagMapper.toTagResponse(tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found")));
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public TagResponse createTag(TagRequest request) {
        if (tagRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.TAG_EXISTED);
        }

        Tag tag = tagMapper.toTag(request);
        tag.setUrlSlug(SlugUtils.toSlug(request.getName()));

        return tagMapper.toTagResponse(tagRepository.save(tag));
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public TagResponse updateTag(String id, TagRequest request) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not fonud"));

        tagMapper.updateTag(tag, request);
        tag.setUrlSlug(SlugUtils.toSlug(request.getName()));

        return tagMapper.toTagResponse(tagRepository.save(tag));
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deleteTag(TagRequest request) {
        Tag result = tagRepository.findByNameAndDescription(request.getName(), request.getDescription())
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tagRepository.delete(result);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deleteTag(String id) {
        tagRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<TagResponse> getTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagResponse> tagResponses = new ArrayList<>();
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
                tagResponses.add(tagMapper.toTagResponse(tag));
            }
        }
        return tagResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public TagResponse getTagByUrlSlug(String urlSlug) {
        Tag tag = tagRepository.findByUrlSlug(urlSlug).orElseThrow(() -> new RuntimeException("Tag not found"));
        return tagMapper.toTagResponse(tag);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<TagResponse> getTags(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tag> tagPage = tagRepository.findAll(pageable);

        List<TagResponse> tagResponses = new ArrayList<>();
        if (tagPage.hasContent()) {
            for (Tag tag : tagPage) {
                tagResponses.add(tagMapper.toTagResponse(tag));
            }
        }
        return tagResponses;
    }
}
