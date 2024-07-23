package com.kpilszak.postcomments.service;

import com.kpilszak.postcomments.client.CommentApiClient;
import com.kpilszak.postcomments.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private CommentApiClient commentApiClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCommentsByPostId() {
        Comment comment1 = new Comment(1L, 1L, "Name1", "email1@example.com", "Body1");
        Comment comment2 = new Comment(2L, 1L, "Name2", "email2@example.com", "Body2");
        List<Comment> comments = List.of(comment1, comment2);

        when(commentApiClient.getCommentsByPostId(1L)).thenReturn(comments);

        List<Comment> result = commentService.getCommentsByPostId(1L);
        assertEquals(2, result.size());
        assertEquals("Name1", result.get(0).getName());
    }

    @Test
    void getCommentById() {
        Comment comment = new Comment(1L, 1L, "Name1", "email1@example.com", "Body1");

        when(commentApiClient.getCommentById(1L, 1L)).thenReturn(comment);

        Comment result = commentService.getCommentById(1L, 1L);
        assertEquals("Name1", result.getName());
        assertEquals("Body1", result.getBody());
    }
}
