package com.darglk.blogcommons.events.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentCreatedEvent {
    private String userId;
    private String commentId;
    private String postId;
}
