package com.signal.domain.post.dto.response;

import com.signal.domain.post.model.Post;
import com.signal.domain.post.model.enums.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private Category category;
    private Long viewCount;
    private Long likesCount;
    private Long commentsCount;

    public static PostResponse toDto(
        Post post
    ) {
        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .category(post.getCategory())
            .createdAt(post.getCreatedAt())
            .viewCount(post.getViewCount())
            .likesCount(post.getLikesCount())
            .commentsCount(post.getCommentCount())
            .build()
            ;
    }
}
