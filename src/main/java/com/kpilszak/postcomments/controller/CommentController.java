package com.kpilszak.postcomments.controller;

import com.kpilszak.postcomments.exception.NotFoundException;
import com.kpilszak.postcomments.model.Comment;
import com.kpilszak.postcomments.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<?> getComments(@PathVariable Long postId, @RequestParam(required = false) Long commentId) {
        try {
            if (commentId == null) {
                List<Comment> comments = commentService.getCommentsByPostId(postId);
                return ResponseEntity.ok(comments);
            } else {
                Comment comment = commentService.getCommentById(postId, commentId);
                return ResponseEntity.ok(comment);
            }
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }
}
