package kg.geektech.android3lesson2.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.android3lesson2.App;
import kg.geektech.android3lesson2.R;
import kg.geektech.android3lesson2.databinding.FragmentFormBinding;
import kg.geektech.android3lesson2.models.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormFragment extends Fragment {
    private FragmentFormBinding binding;
    public static final int USER_ID = 3;
    public static final int GROUP_ID = 36;
    private Post post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getBundle();
        initListeners();
    }

    private void getBundle() {
        if (getArguments() != null) {
            post = (Post) requireArguments().getSerializable("post");
            binding.etTitle.setText(post.getTitle());
            binding.etContent.setText(post.getContent());
        }
    }

    private void initListeners() {
        binding.btnSend.setOnClickListener(v -> {
            String title = binding.etTitle.getText().toString().trim();
            String content = binding.etContent.getText().toString().trim();

            if (getArguments() != null){
                post.setTitle(title);
                post.setContent(content);
                App.api.updatePost(post.getId(), post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        closeFragment();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            } else {
                post = new Post(title, content, USER_ID, GROUP_ID);

                App.api.createPost(post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        closeFragment();
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });
            }

        });
    }

    private void closeFragment(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

}