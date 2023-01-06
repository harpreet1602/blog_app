package com.harpreet.blog.blogappapis.services.impl;

import com.harpreet.blog.blogappapis.entities.Comment;
import com.harpreet.blog.blogappapis.entities.Post;
import com.harpreet.blog.blogappapis.entities.User;
import com.harpreet.blog.blogappapis.exceptions.ResourceNotFoundException;
import com.harpreet.blog.blogappapis.payloads.CommentDto;
import com.harpreet.blog.blogappapis.repositories.CommentRepo;
import com.harpreet.blog.blogappapis.repositories.PostRepo;
import com.harpreet.blog.blogappapis.repositories.UserRepo;
import com.harpreet.blog.blogappapis.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id",userId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id",commentId));
        this.commentRepo.delete(comment);
    }

    @Override
    public Set<CommentDto> getCommentsByPostId(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",postId));

        Set<Comment> comments = post.getComments();
        Set<CommentDto> commentDtos = comments.stream().map((comment)->this.modelMapper.map(comment,CommentDto.class)).collect(Collectors.toSet());
        return commentDtos;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id",commentId));
        comment.setContent(commentDto.getContent());
        Comment updatedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(updatedComment, CommentDto.class);
    }
}
