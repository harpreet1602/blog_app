package com.harpreet.blog.blogappapis.controllers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harpreet.blog.blogappapis.entities.User;
import com.harpreet.blog.blogappapis.exceptions.ApiException;
import com.harpreet.blog.blogappapis.payloads.JwtAuthRequest;
import com.harpreet.blog.blogappapis.payloads.JwtAuthResponse;
import com.harpreet.blog.blogappapis.payloads.UserDto;
import com.harpreet.blog.blogappapis.security.JwtTokenHelper;
import com.harpreet.blog.blogappapis.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper mapper;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request
            ) throws ApiException {
        this.authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response =new JwtAuthResponse();
        response.setToken(token);
        response.setUser(this.mapper.map((User)userDetails,UserDto.class));
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }
    private void authenticate(String username,String password) throws ApiException{
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try{
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch(BadCredentialsException e){
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid username or password");
        }
    }
//    register new user
    @PostMapping("/register")
    ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }
}