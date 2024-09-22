package FA.JustBlog.Core.service;

import java.sql.Date;
import java.util.List;

import FA.JustBlog.Core.models.dto.request.PostRequest;
import FA.JustBlog.Core.models.dto.response.PostResponse;

public interface IPostService {
    public PostResponse getPost(int year, int month, String urlSlug);

    public PostResponse getPost(String id);

    public PostResponse createPost(PostRequest request);

    public PostResponse updatePost(String id, PostRequest request);

    public void deletePost(PostRequest request);

    public void deletePost(String id);

    public List<PostResponse> getPosts();

    public List<PostResponse> getPublisedPosts();

    public List<PostResponse> getUnpublisedPosts();

    public List<PostResponse> getLatestPost(int size);

    public List<PostResponse> getPostsByMonth(Date monthYear);

    public int countPostsForCategory(String category);

    public List<PostResponse> getPostsByCategory(String category);

    public List<PostResponse> getMostViewedPost(int size);

    public List<PostResponse> getHighestPosts(int size);

    public List<PostResponse> getPostsByCategoryUrlSlug(String category);

    public List<PostResponse> getPosts(int page, int size);

    public List<PostResponse> searchPosts(String keyword);

}
