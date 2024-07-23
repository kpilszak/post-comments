package com.kpilszak.postcomments.service;

import com.kpilszak.postcomments.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentsByPostId(Long postId);
    Comment getCommentById(Long postId, Long commentId);

}
