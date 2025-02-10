package com.admin.bharatresult.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.admin.bharatresult.entity.TrendingPost;
import com.admin.bharatresult.repository.TrendingPostRepo;

@Controller
public class TrendingPostController {

    @Autowired
    private TrendingPostRepo trendingPostRepository;

    @GetMapping("/admin/trending/post")
    public ModelAndView trendingPage() {
        List<TrendingPost> allpost = this.trendingPostRepository.findAll();
        if (allpost.size() < 9) {
            for (int i = 0; i < 9; i++) {
                TrendingPost post = new TrendingPost();
                allpost.add(post);
            }
        }
        ModelAndView mv = new ModelAndView("addTrending");
        mv.addObject("posts", allpost);
        return mv;
    }

    // Post mapping to save the posts from the form
    @PostMapping("/admin/trending/post")
    public ModelAndView savePosts(@RequestParam("name[]") List<String> names, @RequestParam("url[]") List<String> urls,
            RedirectAttributes redirectAttributes) {
                System.out.println("names size :"+names.size());
        // Clear any previous data
        trendingPostRepository.deleteAll();
        // Loop through names and URLs and save each post
        List<TrendingPost>postList=new ArrayList();
        for (int i = 0; i < names.size(); i++) {
            TrendingPost post = new TrendingPost();
            post.setName(names.get(i));
            post.setUrl(urls.get(i));
            postList.add(post);
        }
        trendingPostRepository.saveAll(postList);
        redirectAttributes.addFlashAttribute("eventMsg", "Saved...");
        
        return new ModelAndView(new RedirectView("/admin/trending/post"));
    }

}
