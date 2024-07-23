package com.kpilszak.postcomments.client;

import com.kpilszak.postcomments.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
public class CommentApiClientTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommentApiClient commentApiClient;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void getCommentsByPostId() {
        String responseBody = """
                [
                    {
                        "postId": 1,
                        "id": 1,
                        "name": "name1",
                        "email": "email1@example.com",
                        "body": "body1"
                    }
                ]
                """;
        mockServer.expect(requestTo("https://jsonplaceholder.typicode.com/posts/1/comments"))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        List<Comment> comments = commentApiClient.getCommentsByPostId(1L);

        assertEquals(1, comments.size());
        assertEquals("name1", comments.get(0).getName());
    }

    @Test
    public void getCommentsByPostIdNotFound() {
        mockServer.expect(requestTo("https://jsonplaceholder.typicode.com/posts/2/comments"))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.NotFound.class, () -> {
            commentApiClient.getCommentsByPostId(2L);
        });
    }

}
