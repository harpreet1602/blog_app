package com.harpreet.blog.blogappapis.controllers;
import com.harpreet.blog.blogappapis.payloads.ApiResponse;
import com.harpreet.blog.blogappapis.payloads.CommentDto;
import com.harpreet.blog.blogappapis.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;
@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;
//Create the comment
    @PostMapping("/post/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId){
        CommentDto createComment = this.commentService.createComment(commentDto,postId,userId);
        return new ResponseEntity<>(createComment, HttpStatus.OK);
    }
// Delete the comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable("commentId") Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment Deleted Successfully",true),HttpStatus.OK);
    }
//    Get the set of comments
    @GetMapping("/post/{postId}/comments")
    public ResponseEntity<Set<CommentDto>> getCommentByPostId(@PathVariable("postId") Integer postId){
        Set<CommentDto> commentDtos = this.commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);
    }
//    Update the comment.
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,@PathVariable("commentId") Integer commentId){
        CommentDto updatedComment = this.commentService.updateComment(commentDto,commentId);
        return new ResponseEntity<>(updatedComment,HttpStatus.OK);
    }
}