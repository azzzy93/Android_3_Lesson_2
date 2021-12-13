package kg.geektech.android3lesson2.ui.posts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import kg.geektech.android3lesson2.App;
import kg.geektech.android3lesson2.R;
import kg.geektech.android3lesson2.databinding.FragmentPostsBinding;
import kg.geektech.android3lesson2.interfaces.MyOnItemClickListeners;
import kg.geektech.android3lesson2.models.Post;
import kg.geektech.android3lesson2.ui.form.FormFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsFragment extends Fragment {
    private FragmentPostsBinding binding;
    private PostsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostsAdapter();

        adapter.setMyOnItemClickListeners(new MyOnItemClickListeners() {
            @Override
            public void onClick(int position) {
                Post post = adapter.getPost(position);
                if (post.getUserId() == FormFragment.USER_ID) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("post", post);
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.formFragment, bundle);
                } else {
                    Toast.makeText(requireActivity(), "Это не ваш пост, вы не можете его отредактировать.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(int position) {
                Post post = adapter.getPost(position);
                if (post.getUserId() == FormFragment.USER_ID) {
                    App.api.deletePost(post.getId()).enqueue(new Callback<Post>() {
                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {
                            adapter.deletePost(position);
                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(requireActivity(), "Это не ваш пост, вы не можете его удалить.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.recycler.setAdapter(adapter);

        App.api.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setPosts(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });

        binding.fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(R.id.formFragment);
        });
    }
}