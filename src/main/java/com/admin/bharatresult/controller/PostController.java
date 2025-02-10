package com.admin.bharatresult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.admin.bharatresult.entity.Category;
import com.admin.bharatresult.entity.Post;
import com.admin.bharatresult.entity.User;
import com.admin.bharatresult.service.CategoryService;
import com.admin.bharatresult.service.PostService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class PostController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PostService postService;

    @GetMapping("/admin/posts")
    public ModelAndView posts() {
        List<Post> allPosts = this.postService.getAllPosts();
        ModelAndView mv = new ModelAndView("blog-post");
        mv.addObject("posts", allPosts);
        return mv;
    }

    @GetMapping("/admin/post/add")
    public ModelAndView addPost() {
        List<Category> categories = this.categoryService.getCategories();
        ModelAndView mv = new ModelAndView("addPost");
        mv.addObject("categories", categories);
        return mv;
    }

    @GetMapping("/admin/post/update")
    public ModelAndView updatePostView() {
        List<Category> categories = this.categoryService.getCategories();
        ModelAndView mv = new ModelAndView("updatePost");
        mv.addObject("categories", categories);
        return mv;
    }

    @GetMapping("/admin/post/update/{id}")
    public ModelAndView updatePost(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        List<Category> categories = this.categoryService.getCategories();
        Post post = this.postService.getPost(id);
        if (post == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "Post Not Exist with Id: " + id);
            return new ModelAndView(new RedirectView("/admin/post/update"));
        }
        ModelAndView mv = new ModelAndView("updatePost");
        mv.addObject("categories", categories);
        mv.addObject("post", post);
        return mv;
    }

    @PostMapping("/admin/blog/add")
    public ModelAndView savePost(@ModelAttribute Post post, RedirectAttributes redirectAttributes,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        boolean savedPost = this.postService.savePost(post, user);
        if (savedPost) {
            redirectAttributes.addFlashAttribute("eventMsg", "Post Saved...");
            return new ModelAndView(new RedirectView("/admin/post/add"));
        }
        redirectAttributes.addFlashAttribute("errorMsg", "Something is Wrong! Please check Post Url ...");
        redirectAttributes.addFlashAttribute("post", post);
        return new ModelAndView(new RedirectView("/admin/post/add"));
    }

    @PostMapping("/admin/blog/update")
    public ModelAndView updatePost(@ModelAttribute Post post, RedirectAttributes redirectAttributes,
            HttpSession session) {
        User user = (User) session.getAttribute("user");
        Long postId = post.getId();
        boolean savedPost = this.postService.savePost(post, user);
        if (savedPost) {
            redirectAttributes.addFlashAttribute("eventMsg", "Post updated...");
            return new ModelAndView(new RedirectView("/admin/post/update/" + postId));
        }
        redirectAttributes.addFlashAttribute("errorMsg", "Something is Wrong! Please check Post Url ...");
        return new ModelAndView(new RedirectView("/admin/post/update/" + postId));
    }

    @GetMapping("/admin/blog/update")
    public ModelAndView updateBlogByPostId(@RequestParam("postId") Long id) {
        return new ModelAndView(new RedirectView("/admin/post/update/" + id));
    }

    @GetMapping("/admin/blog/toggle/{id}")
    public ModelAndView togglePostView(@PathVariable("id") Long id, HttpSession session,
            RedirectAttributes redirectAttributes) {
        // Retrieve post by ID
        Post post = this.postService.getPost(id);

        // Check if the post exists and user is valid
        if (post != null) {
            // Toggle the publish status
            boolean newStatus = !post.isPublish(); // If it's true, set it to false and vice versa
            post.setPublish(newStatus);
            
            // Save the updated post
            this.postService.savePost(post, post.getUser());

            // Prepare the success message based on the new status
            String statusMessage = newStatus ? "Post published." : "Post unpublished.";
            redirectAttributes.addFlashAttribute("eventMsg", statusMessage);

            // Redirect back to the posts page
            return new ModelAndView(new RedirectView("/admin/posts"));
        }

        // In case post or user is not found, show an error message
        redirectAttributes.addFlashAttribute("errorMsg", "Something went wrong. Please try again.");
        return new ModelAndView(new RedirectView("/admin/posts"));
    }

    @GetMapping("/admin/blog/delete/{id}")
    public ModelAndView deleteBlog(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        this.postService.deletePost(id);
        redirectAttributes.addFlashAttribute("eventMsg", "Post deleted...");
        return new ModelAndView(new RedirectView("/admin/posts"));
    }

}
