package com.harpreet.blog.blogappapis.security;

import com.harpreet.blog.blogappapis.entities.User;
import com.harpreet.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.harpreet.blog.blogappapis.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        loading user from database.
        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email",username));

        return user;
    }
}
