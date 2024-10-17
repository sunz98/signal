package com.signal.domain.comment.controller;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.signal.domain.comment.dto.resquest.CommentCreateRequest;
import com.signal.domain.comment.dto.resquest.CommentUpdateRequest;
import com.signal.domain.comment.model.Comment;
import com.signal.domain.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@Operation(summary = "댓글 생성")
	@PostMapping("/common/post/{postId}/comment")
	public ResponseEntity<Void> createComment(@RequestBody CommentCreateRequest request){
		commentService.createComment(request);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary="댓글 수정")
	@PutMapping("/common/post/{postId}/comment/{commentId}")
	public ResponseEntity<Void> updateComment(
			@PathVariable Long postId,
			@PathVariable Long commentId,
			@RequestBody CommentUpdateRequest request){
			commentService.updateComment(commentId, request);
			return ResponseEntity.ok().build();
		
	}
	@Operation(summary="댓글 삭제")
	@DeleteMapping("/common/post/{postId}/comment/{commentId}")
	public ResponseEntity<Void> deleteComment(
			@PathVariable Long postId,
			@PathVariable Long commentId
			){
			commentService.deleteComment(commentId);
				return ResponseEntity.ok().build();
		
	}
	
	@Operation(summary="댓글 조회")
	@GetMapping("/common/post/{postId}/comment")
	public ResponseEntity<Page<Comment>> getCommentByPostId(
			@PathVariable Long postId,
			Pageable pageable
			){
		Page<Comment> comments=commentService.getCommentByPostID(postId, pageable);
		return ResponseEntity.ok().build();
		
	}
	
	
    
}
