package com.harpreet.blog.blogappapis.controllers;

import com.harpreet.blog.blogappapis.payloads.ApiResponse;
import com.harpreet.blog.blogappapis.payloads.PostDto;
import com.harpreet.blog.blogappapis.payloads.PostResponse;
import com.harpreet.blog.blogappapis.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
//    create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        PostDto postDto1 = this.postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }
//  Get Post by User
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
        List<PostDto> postDtos = this.postService.getPostsByUser(userId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
//    get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
        List<PostDto> postDtos = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

//    Get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize
    ){
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
    }

//    Get Post By post id
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = this.postService.getPostById(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

//  Update the Post
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
        PostDto updatedPost = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
//    Delete the Post
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
    }
}
