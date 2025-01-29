package com.admin.bharatresult.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@Controller
public class HomeContoller {

	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/")
	public ModelAndView homePage() {
		return new ModelAndView("login", HttpStatus.OK);
	}

	@GetMapping("/admin/signin")
	public ModelAndView signInPage() {
		return new ModelAndView("login", HttpStatus.OK);
	}

	@GetMapping("/admin/dashboard")
	public ModelAndView dashboardPage(HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView();
		if (session.getAttribute("user") != null) {
			List<Post> allPosts = postService.getAllPosts();
			List<Category> categories = this.categoryService.getCategories();
			User user = (User) session.getAttribute("user");
			mv.addObject("user", user);
			mv.addObject("posts", allPosts);
			mv.addObject("categories", categories);
			mv.setViewName("index");
			return mv;
		}

		redirectAttributes.addFlashAttribute("errorMsg", "Please Sign In!");
		mv.setView(new RedirectView("/admin/signin"));
		return mv;
	}

}
