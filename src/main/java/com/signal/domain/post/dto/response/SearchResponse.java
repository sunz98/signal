package com.signal.domain.post.dto.response;

import com.signal.domain.post.model.Post;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.annotation.MergedAnnotations.Search;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchResponse {

    private int totalCount;
    private PostResponse hotPost;
    private List<PostResponse> posts;

    public static SearchResponse toDto (
        int totalCount,
        PostResponse hotPost,
        List<PostResponse> posts
    ) {
        return SearchResponse.builder()
            .totalCount(totalCount)
            .hotPost(hotPost)
            .posts(posts)
            .build()
            ;
    }
}
