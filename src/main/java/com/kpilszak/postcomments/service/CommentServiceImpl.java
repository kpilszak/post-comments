    package com.kpilszak.postcomments.service;

    import com.kpilszak.postcomments.client.CommentApiClient;
    import com.kpilszak.postcomments.exception.NotFoundException;
    import com.kpilszak.postcomments.model.Comment;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;
    import org.springframework.web.client.HttpClientErrorException;

    import java.util.List;

    @Service
    @Slf4j
    public class CommentServiceImpl implements CommentService {

        private final CommentApiClient commentApiClient;

        public CommentServiceImpl(CommentApiClient commentApiClient) {
            this.commentApiClient = commentApiClient;
        }

        @Override
        public List<Comment> getCommentsByPostId(Long postId) {
            log.info("Fetching all comments for post id: {}", postId);
            try {
                return commentApiClient.getCommentsByPostId(postId);
            } catch (HttpClientErrorException.NotFound ex) {
                log.error("Comments not found for post id: {}", postId);
                throw new NotFoundException("Comments not found for post id: " + postId);
            } catch (Exception ex) {
                log.error("Unexpected error occurred while fetching comments for post id: {}", postId, ex);
                throw new RuntimeException("An unexpected error occurred while fetching comments", ex);
            }
        }

        @Override
        public Comment getCommentById(Long postId, Long commentId) {
            log.info("Fetching comment with id: {} for post id: {}", commentId, postId);
            try {
                Comment comment = commentApiClient.getCommentById(postId, commentId);
                if (comment == null) {
                    throw new NotFoundException("Comment not found with id: " + commentId);
                }
                return comment;
            } catch (HttpClientErrorException.NotFound ex) {
                log.error("Comment not found with id: {} for post id: {}", commentId, postId);
                throw new NotFoundException("Comment not found with id: " + commentId);
            } catch (Exception ex) {
                log.error("Unexpected error occurred while fetching comment with id: {} for post id: {}", commentId, postId, ex);
                throw new RuntimeException("An unexpected error occurred while fetching comment", ex);
            }
        }
    }
