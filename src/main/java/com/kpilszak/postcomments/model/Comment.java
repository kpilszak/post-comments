package com.kpilszak.postcomments.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {

    private Long id;
    private Long postId;
    private String name;
    private String email;
    private String body;
    
}
