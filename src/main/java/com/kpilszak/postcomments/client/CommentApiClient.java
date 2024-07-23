package com.kpilszak.postcomments.client;

import com.kpilszak.postcomments.model.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Component
public class CommentApiClient {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://jsonplaceholder.typicode.com/posts";

    public CommentApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
                .pathSegment(postId.toString(), "comments")
                .build()
                .toUri();
        Comment[] comments = restTemplate.getForObject(uri, Comment[].class);
        return List.of(comments);
    }

    public Comment getCommentById(Long postId, Long commentId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
                .pathSegment(postId.toString(), "comments", commentId.toString())
                .build()
                .toUri();
        return restTemplate.getForObject(uri, Comment.class);
    }
}
