package com.admin.bharatresult.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.bharatresult.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

}
