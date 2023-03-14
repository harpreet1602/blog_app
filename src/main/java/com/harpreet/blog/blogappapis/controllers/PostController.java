package com.harpreet.blog.blogappapis.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.harpreet.blog.blogappapis.config.AppConstants;
import com.harpreet.blog.blogappapis.payloads.ApiResponse;
import com.harpreet.blog.blogappapis.payloads.PostDto;
import com.harpreet.blog.blogappapis.payloads.PostResponse;
import com.harpreet.blog.blogappapis.services.FileService;
import com.harpreet.blog.blogappapis.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@RestController
@RequestMapping("/api/v1")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    private Logger logger = LoggerFactory.getLogger(PostController.class);
    @Autowired
    private ObjectMapper mapper;
    @Value("${project.image}")
    private String path;
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
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
    ){
        PostResponse postResponse = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
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
//    Search
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
        @PathVariable("keywords") String keywords
    ){
        List<PostDto> postDtos = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }
//    post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postId") Integer postId
            ) throws IOException {
                PostDto postDto = this.postService.getPostById(postId);
                String fileName = this.fileService.uploadImage(path,image);
                postDto.setImageName(fileName);
                PostDto updatePost = this.postService.updatePost(postDto,postId);
                return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }
//    method to serve files
//    http://localhost:9090/api/post/image/10feb0da-8803-4c32-8cda-1a8bbca3b593.jpeg
    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
        @PathVariable("imageName") String imageName,
        HttpServletResponse response
    ) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
//  Json and file together in one API
    @PostMapping("/user/{userId}/category/{categoryId}/posts/image/upload")
    public ResponseEntity<PostDto> createUploadPost(@RequestParam("file") MultipartFile file,
                                              @RequestParam("postData") String postData,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId
    ) throws IOException, JsonProcessingException{
        this.logger.info("Create Post and Upload Image together");
        this.logger.info("file: {}", file.getOriginalFilename());
        this.logger.info("postData: {}", postData);
        PostDto postDto = null;
        postDto = mapper.readValue(postData, PostDto.class);
//        try {
//            postDto = mapper.readValue(postData, PostDto.class);
//        }
//        catch(JsonProcessingException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Request");
//        }
        this.logger.info("postDto: {}", postDto.getTitle());
        PostDto postDto1 = this.postService.createPost(postDto,userId,categoryId);
        String fileName = this.fileService.uploadImage(path,file);
        postDto.setImageName(fileName);
        PostDto updatePost = this.postService.updatePost(postDto,postDto1.getPostId());
        return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }
}