package com.harpreet.blog.blogappapis.repositories;

import com.harpreet.blog.blogappapis.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
