package com.signal.domain.likes.controller;

import com.signal.domain.likes.service.LikesService;
import com.signal.global.sercurity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/common/post/{postId}/like")
@Tag(name = "like", description = "좋아요 기능")
public class LikesController {

    private final LikesService likesService;

    @Operation(summary = "좋아요 추가/삭제")
    @PostMapping
    public ResponseEntity<String> likesPost(
        @PathVariable Long postId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();
        return ResponseEntity.ok(likesService.likesPost(postId, userId));
    }
}
