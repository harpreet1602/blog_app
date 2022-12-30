package com.harpreet.blog.blogappapis.repositories;

import com.harpreet.blog.blogappapis.entities.Category;
import com.harpreet.blog.blogappapis.entities.Post;
import com.harpreet.blog.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);
}
