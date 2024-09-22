package FA.JustBlog.Core.service.serviceImpl;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import FA.JustBlog.Core.exception.AppException;
import FA.JustBlog.Core.exception.ErrorCode;
import FA.JustBlog.Core.mapper.PostMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import FA.JustBlog.Core.models.Category;
import FA.JustBlog.Core.models.Post;
import FA.JustBlog.Core.models.Tag;
import FA.JustBlog.Core.models.dto.request.PostRequest;
import FA.JustBlog.Core.models.dto.response.PostResponse;
import FA.JustBlog.Core.repositories.ICategoryRepository;
import FA.JustBlog.Core.repositories.IPostRepository;
import FA.JustBlog.Core.repositories.ITagRepository;
import FA.JustBlog.Core.service.IPostService;
import FA.JustBlog.Core.utils.SlugUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostServiceImpl implements IPostService {
    IPostRepository postRepository;
    ICategoryRepository categoryRepository;
    ITagRepository tagRepository;
    PostMapper postMapper;

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public PostResponse getPost(int year, int month, String urlSlug) {
        List<Post> posts = postRepository.findByUrlSlug(urlSlug);
        for (Post p : posts) {
            if (p.getPublished().getYear() == year) {
                if (p.getPublished().getMonthValue() == month) {
                    return postMapper.toPostResponse(p);
                }
            }
        }
        return null;
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public PostResponse getPost(String id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        PostResponse postResponse = postMapper.toPostResponse(post);
        postResponse.setCategoryId(post.getCategory().getId());

        List<String> tagsId = new ArrayList<>();
        Set<Tag> tags = tagRepository.findByPostId(id);
        for (Tag tag : tags) {
            tagsId.add(tag.getId());
        }
        postResponse.setTagId(tagsId);

        return postResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public PostResponse createPost(PostRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (postRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.POST_EXISTED);
        }

        Post post = Post.builder()
                .urlSlug(SlugUtils.toSlug(request.getTitle()))
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .postContent(request.getPostContent())
                .published(LocalDateTime.now())
                .posterOn(true)
                .modified(false)
                .category(category)
                .build();

        if (!request.getTagId().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (int i = 0; i < request.getTagId().size(); i++) {
                tags.add(tagRepository.findById(request.getTagId().get(i)).orElse(null));
            }
            post.setTags(tags);
        }

        Post savedPost = postRepository.save(post);

        PostResponse postResponse = postMapper.toPostResponse(savedPost);
        postResponse.setCategoryId(savedPost.getCategory().getId());
        postResponse.setTagId(request.getTagId());

        return postResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('CONTRIBUTOR', 'BLOG_OWNER')")
    public PostResponse updatePost(String id, PostRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        post.builder()
                .urlSlug(SlugUtils.toSlug(request.getTitle()))
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .postContent(request.getPostContent())
                .modified(true)
                .build();

        if (!request.getTagId().isEmpty()) {
            Set<Tag> tags = new HashSet<>();
            for (int i = 0; i < request.getTagId().size(); i++) {
                tags.add(tagRepository.findById(request.getTagId().get(i)).orElse(null));
            }
            post.setTags(tags);
        }

        Post postUpdate = postRepository.save(post);
        PostResponse postResponse = postMapper.toPostResponse(postUpdate);
        postResponse.setCategoryId(postUpdate.getCategory().getId());
        postResponse.setTagId(request.getTagId());

        return postResponse;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deletePost(PostRequest request) {
        Post result = postRepository.findByTitleAndShortDescriptionAndPostContent(request.getTitle(),
                request.getShortDescription(), request.getPostContent()).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(result);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }
            for (int i = 0; i < posts.size(); i++) {
                postResponses.get(i).setPublished(posts.get(i).getPublished());
                postResponses.get(i).setCategoryId(posts.get(i).getCategory().getId());

                List<String> tagsId = new ArrayList<>();
                Set<Tag> tags = tagRepository.findByPostId(postResponses.get(i).getId());
                for (Tag tag : tags) {
                    tagsId.add(tag.getId());
                }
                postResponses.get(i).setTagId(tagsId);
            }
        }


        return postResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getPublisedPosts() {
        List<Post> posts = postRepository.findByPosterOn(true);
        List<PostResponse> postResponses = new ArrayList<>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }
        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getUnpublisedPosts() {
        List<Post> posts = postRepository.findByPosterOn(false);
        List<PostResponse> postResponses = new ArrayList<>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }
        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getLatestPost(int size) {
        List<Post> posts = postRepository.findByPublished(LocalDateTime.now());
        List<PostResponse> postResponses = new ArrayList<>();
        if (posts.isEmpty()) {
            return null;
        } else if (posts.size() <= size) {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }
        } else {
            for (int i = posts.size() - 1; i > posts.size() - size - 1; i--) {
                postResponses.add(postMapper.toPostResponse(posts.get(i)));
            }
        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getPostsByMonth(Date monthYear) {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                if (post.getPublished().getMonthValue() == monthYear.getMonth() + 1
                        && post.getPublished().getYear() == monthYear.getYear() + 1900) {
                    postResponses.add(postMapper.toPostResponse(post));
                }
            }
        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public int countPostsForCategory(String category) {
        Category result = categoryRepository.findByName(category).orElse(null);
        List<Post> posts = postRepository.findByCategory(result);
        return posts.size();
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public List<PostResponse> getPostsByCategory(String category) {
        Category result = categoryRepository.findByName(category).orElse(null);
        List<Post> posts = postRepository.findByCategory(result);
        List<PostResponse> postResponses = new ArrayList<>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }

            for (int i = 0; i < posts.size(); i++) {
                postResponses.get(i).setCategoryId(posts.get(i).getCategory().getId());
            }

        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getMostViewedPost(int size) {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        posts.sort((post1, post2) -> Integer.compare(post2.getViewCount(), post1.getViewCount()));
        if (size <= posts.size()) {
            for (int i = 0; i < size; i++) {
                postResponses.add(postMapper.toPostResponse(posts.get(i)));
            }
        } else {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }
        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getHighestPosts(int size) {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        posts.sort((post1, post2) -> Double.compare(Math.floor(post2.getTotalRate() / post2.getRateCount()) * 10 % 10,
                Math.floor(post1.getTotalRate() / post1.getRateCount()) * 10 % 10));
        if (size <= posts.size()) {
            for (int i = 0; i < size; i++) {
                postResponses.add(postMapper.toPostResponse(posts.get(i)));
            }
        } else {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }
        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public List<PostResponse> getPostsByCategoryUrlSlug(String urlSlug) {
        Category result = categoryRepository.findByUrlSlug(urlSlug).orElse(null);
        List<Post> posts = postRepository.findByCategory(result);
        List<PostResponse> postResponses = new ArrayList<>();
        if (!posts.isEmpty()) {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }

            for (int i = 0; i < posts.size(); i++) {
                postResponses.get(i).setPublished(posts.get(i).getPublished());
                postResponses.get(i).setCategoryId(posts.get(i).getCategory().getId());

                List<String> tagsId = new ArrayList<>();
                Set<Tag> tags = tagRepository.findByPostId(postResponses.get(i).getId());
                for (Tag tag : tags) {
                    tagsId.add(tag.getId());
                }
                postResponses.get(i).setTagId(tagsId);
            }
        }
        return postResponses;
    }

    @Override
    @PreAuthorize("hasRole('BLOG_OWNER')")
    public List<PostResponse> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findAll(pageable);
        List<PostResponse> postResponses = new ArrayList<>();

        if (!postsPage.hasContent()) {
            return Collections.emptyList();
        }

        for (Post post : postsPage) {
            PostResponse postResponse = postMapper.toPostResponse(post);
            postResponse.setPublished(post.getPublished());

            if (post.getCategory() != null) {
                postResponse.setCategoryId(post.getCategory().getId());
            }

            List<String> tagsId = new ArrayList<>();
            Set<Tag> tags = tagRepository.findByPostId(post.getId());
            for (Tag tag : tags) {
                tagsId.add(tag.getId());
            }
            postResponse.setTagId(tagsId);

            postResponses.add(postResponse);
        }

        return postResponses;
    }


    @Override
    @PreAuthorize("hasAnyRole('USER', 'CONTRIBUTOR', 'BLOG_OWNER')")
    public List<PostResponse> searchPosts(String keyword) {
        List<Post> posts = postRepository.searchByTitleOrShortDescription(keyword);
        List<PostResponse> postResponses = new ArrayList<>();

        if (!posts.isEmpty()) {
            for (Post post : posts) {
                postResponses.add(postMapper.toPostResponse(post));
            }

            for (int i = 0; i < posts.size(); i++) {
                postResponses.get(i).setPublished(posts.get(i).getPublished());
                postResponses.get(i).setCategoryId(posts.get(i).getCategory().getId());

                List<String> tagsId = new ArrayList<>();
                Set<Tag> tags = tagRepository.findByPostId(postResponses.get(i).getId());
                for (Tag tag : tags) {
                    tagsId.add(tag.getId());
                }
                postResponses.get(i).setTagId(tagsId);
            }
        }
        return postResponses;
    }
}
