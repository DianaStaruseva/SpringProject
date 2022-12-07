package com.example.demo.viewModels;

import com.example.demo.models.Post;

public class EmployeePostViewModel {
    private Iterable<Post> posts;
    private Post selectedPost;

    public EmployeePostViewModel(Iterable<Post> posts) {
        this.posts = posts;
    }

    public Iterable<Post> getPosts() {
        return posts;
    }

    public void setPosts(Iterable<Post> posts) {
        this.posts = posts;
    }

    public Post getSelectedPost() {
        return selectedPost;
    }

    public void setSelectedPost(Post selectedPost) {
        this.selectedPost = selectedPost;
    }
}
