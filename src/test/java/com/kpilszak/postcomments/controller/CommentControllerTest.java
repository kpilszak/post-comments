package com.kpilszak.postcomments.controller;

import com.kpilszak.postcomments.exception.NotFoundException;
import com.kpilszak.postcomments.model.Comment;
import com.kpilszak.postcomments.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
    }

    @Test
    void getAllComments() throws Exception {
        List<Comment> comments = Arrays.asList(
                new Comment(1L, 1L, "Name1", "email1@example.com", "Body1"),
                new Comment(2L, 1L, "Name2", "email2@example.com", "Body2")
        );

        when(commentService.getCommentsByPostId(1L)).thenReturn(comments);

        mockMvc.perform(get("/posts/1/comments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Name1"));
    }

    @Test
    void getCommentById() throws Exception {
        Comment comment = new Comment(1L, 1L, "Name1", "email1@example.com", "Body1");

        when(commentService.getCommentById(1L, 1L)).thenReturn(comment);

        mockMvc.perform(get("/posts/1/comments?commentId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name1"))
                .andExpect(jsonPath("$.body").value("Body1"));
    }

    @Test
    void getCommentByIdNotFound() throws Exception {
        when(commentService.getCommentById(1L, 999L)).thenThrow(new NotFoundException("Comment not found"));

        mockMvc.perform(get("/posts/1/comments?commentId=999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}