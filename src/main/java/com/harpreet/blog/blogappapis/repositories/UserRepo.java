package com.harpreet.blog.blogappapis.repositories;

import com.harpreet.blog.blogappapis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {

}
