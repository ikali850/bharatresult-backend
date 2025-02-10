package com.admin.bharatresult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.bharatresult.entity.TrendingPost;

@Repository
public interface TrendingPostRepo extends JpaRepository<TrendingPost,Long>{

    
} 