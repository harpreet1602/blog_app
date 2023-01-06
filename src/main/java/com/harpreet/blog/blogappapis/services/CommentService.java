package com.harpreet.blog.blogappapis.services;

import com.harpreet.blog.blogappapis.entities.Comment;
import com.harpreet.blog.blogappapis.payloads.CommentDto;
import org.apache.catalina.LifecycleState;

import java.util.List;
import java.util.Set;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer postId,Integer userId);
    void deleteComment(Integer commentId);
    Set<CommentDto> getCommentsByPostId(Integer postId);
    CommentDto updateComment(CommentDto commentDto,Integer commentId);
}
