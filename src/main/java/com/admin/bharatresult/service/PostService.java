package com.admin.bharatresult.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.bharatresult.entity.Post;
import com.admin.bharatresult.entity.User;
import com.admin.bharatresult.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    public Post getPost(Long id) {
        return this.postRepository.findById(id).orElse(null);
    }

    public Post getPostByPostUrl(String url) {
        return this.postRepository.findByPostUrl(url).orElse(null);
    }

    public boolean savePost(Post post, User user) {
        post.setUser(user);
        try {
            this.postRepository.save(post);
            return true;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
    }

    public boolean updatePost(Post post, User user) {
        boolean dbpost = this.postRepository.findById(post.getId()).isPresent();
        if (dbpost) {
            post.setUser(user);
            try {
                this.postRepository.save(post);
                return true;
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                return false;
            }
        }
        return false;

    }

    public boolean deletePost(Long id) {
        this.postRepository.deleteById(id);
        return true;
    }

}
