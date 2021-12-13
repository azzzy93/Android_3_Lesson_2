package kg.geektech.android3lesson2.ui.posts;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kg.geektech.android3lesson2.databinding.ItemPostBinding;
import kg.geektech.android3lesson2.interfaces.MyOnItemClickListeners;
import kg.geektech.android3lesson2.models.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostsViewHolder> {
    private ItemPostBinding binding;
    private List<Post> posts;
    private MyOnItemClickListeners myOnItemClickListeners;

    public PostsAdapter() {
        posts = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPosts(List<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        holder.onBind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public Post getPost(int position) {
        return posts.get(position);
    }

    public void setMyOnItemClickListeners(MyOnItemClickListeners myOnItemClickListeners) {
        this.myOnItemClickListeners = myOnItemClickListeners;
    }

    public void deletePost(int position) {
        posts.remove(position);
        notifyItemRemoved(position);
    }


    protected class PostsViewHolder extends RecyclerView.ViewHolder {
        private ItemPostBinding binding;

        public PostsViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(v -> {
                myOnItemClickListeners.onClick(getAdapterPosition());
            });

            itemView.setOnLongClickListener(v -> {
                myOnItemClickListeners.onLongClick(getAdapterPosition());
                return true;
            });
        }

        public void onBind(Post post) {
            binding.tvUserId.setText(getUserName(post.getUserId()));
            binding.tvTitle.setText(post.getTitle());
            binding.tvContent.setText(post.getContent());
        }

        private String getUserName(int position) {
            Map<Integer, String> userName = new HashMap<>();
            userName.put(0, "Учитель");
            userName.put(1, "Арслан");
            userName.put(2, "Шамсуддин");
            userName.put(3, "Азизбек");
            userName.put(4, "Жаркынай");
            userName.put(5, "Дастан Н.");
            userName.put(6, "Нурсамад");
            userName.put(7, "Дастан К.");
            userName.put(8, "Евгения");
            userName.put(9, "Анипа");
            return userName.get(position);
        }
    }
}
