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
import com.admin.bharatresult.repository.CategoryRepository;
import com.admin.bharatresult.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public ModelAndView getCategory() {
        List<Category> categories = categoryService.getCategories();
        ModelAndView mv = new ModelAndView("category");
        mv.addObject("categories", categories);
        return mv;
    }

    @PostMapping("/admin/category")
    public ModelAndView addCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        try {
            this.categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("eventMsg", "Category Added..");
            return new ModelAndView(new RedirectView("/admin/category"));
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "Duplicate Category..");
            return new ModelAndView(new RedirectView("/admin/category"));
        }

    }

    @GetMapping("/admin/category/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        this.categoryService.deleteCategory(id);
        redirectAttributes.addFlashAttribute("eventMsg", "Category Deleted..");
        return new ModelAndView(new RedirectView("/admin/category"));
    }

    @PostMapping("/admin/category/update")
    public ModelAndView updateCategory(@ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        Category categorydb = this.categoryService.getCategory(category.getId());
        if (categorydb != null) {
            this.categoryService.saveCategory(category);
            redirectAttributes.addFlashAttribute("eventMsg", "Category Updated..");
            return new ModelAndView(new RedirectView("/admin/category"));
        }

        redirectAttributes.addFlashAttribute("errorMsg", "Category Does Not Exist..");
        return new ModelAndView(new RedirectView("/admin/category"));
    }

}
