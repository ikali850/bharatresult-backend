package com.admin.bharatresult.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.bharatresult.entity.Category;
import com.admin.bharatresult.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public Category getCategory(Long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    public boolean deleteCategory(Long id) {
        this.categoryRepository.deleteById(id);
        return true;
    }

}
