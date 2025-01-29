package com.admin.bharatresult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.admin.bharatresult.entity.Messages;

@Repository
public interface MessageRepository extends JpaRepository<Messages,Long>{

}
