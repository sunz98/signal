package com.signal.domain.post.controller;

import com.signal.domain.post.dto.request.PostRequest;
import com.signal.domain.post.dto.response.PostDetailResponse;
import com.signal.domain.post.dto.response.PostResponse;
import com.signal.domain.post.dto.response.SearchResponse;
import com.signal.domain.post.model.enums.Category;
import com.signal.domain.post.service.PostService;
import com.signal.global.dto.PagedDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Tag(name = "post", description = "게시판")
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시판 전체 조회")
    @GetMapping("/commmon/post")
    public ResponseEntity<PagedDto<SearchResponse>> getPosts (
        @RequestParam Category category,
        @RequestParam(required = false, value = "size", defaultValue = "10") int size,
        @RequestParam(required = false, value = "page", defaultValue = "0") int page
    ) {
        PagedDto<SearchResponse> posts = postService.getPosts(category, size, page);

        return ResponseEntity.ok(posts);
    }

    @Operation(summary = "게시판 단일 조회")
    @GetMapping("/common/post/{postId}")
    public ResponseEntity<PostDetailResponse> getPostById(@PathVariable Long postId) {
        PostDetailResponse PostDetailResponse = postService.getPostById(postId);
        return ResponseEntity.ok(PostDetailResponse);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @Operation(summary = "게시글 작성")
    @PostMapping("/user/post")
    public ResponseEntity<String> createPost(
        @Valid @RequestBody PostRequest postReqeust
//        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
//        Long userId = customUserDetails.getUserId();
        return ResponseEntity.ok(postService.createPost(postReqeust, 1L));
    }
}
