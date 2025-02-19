package com.admin.bharatresult.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.bharatresult.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

    Optional<Post> findByPostUrl(String url);

}
