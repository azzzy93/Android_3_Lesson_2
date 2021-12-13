package kg.geektech.android3lesson2.data.remote;

import java.util.List;

import kg.geektech.android3lesson2.models.Post;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HerokuApi {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createPost(@Body Post post);

    @DELETE("posts/{id}")
    Call<Post> deletePost(@Path("id") int id);

    @PUT("posts/{id}")
    Call<Post> updatePost(@Path("id") int id, @Body Post post);
}
