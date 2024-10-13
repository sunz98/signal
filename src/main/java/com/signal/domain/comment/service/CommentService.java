package com.signal.domain.comment.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.signal.domain.auth.repository.UserRepository;
import com.signal.domain.comment.dto.resquest.CommentCreateRequest;
import com.signal.domain.comment.dto.resquest.CommentUpdateRequest;
import com.signal.domain.comment.model.Comment;
import com.signal.domain.comment.repository.CommentRepository;
import com.signal.domain.post.repository.PostRepository;
import com.signal.domain.post.service.ChatGPTServiceImpl;
import com.signal.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CommentService {
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	@Transactional
	public void createComment(CommentCreateRequest request) {
	    var post = postRepository.findById(request.getPostId())
	            .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
	    
	    var user = userRepository.findById(request.getUserId())
	            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));

	    Comment comment = new Comment(post, user, request.getContents());
	    commentRepository.save(comment);
	    log.info("댓글 작성 완료: {}", comment);
	}

	@Transactional
	public void updateComment(Long commentId,CommentUpdateRequest request) {
		Comment comment=commentRepository.findById(commentId)
				.orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다"));
		
		
		/*
		  comment.setContents(request.getContents());
		  // or
		  comment.setContents(newContents) // 위에 CommentUpdateRequest request를 String 로 받아오는 경우도 가능
		*/
		comment.updateCotents(request.getContents()); //위의 방식들도 가능하나 이 방식이 더 효율적
		log.info("댓글 수정 완료: {}",comment);
	}
	
	@Transactional
	public void deleteComment(Long commentId) {
		if(!commentRepository.existsById(commentId)) {
			throw new IllegalArgumentException("해당 댓글을 찾을 수 없습니다.");
		}
		commentRepository.deleteById(commentId);
		log.info("댓글 삭제 완료: 댓글 ID{}",commentId);
	}
	
	
	
	
	public Page<Comment> getCommentByPostID(Long postId,Pageable pageable){
		postRepository.findById(postId)
			.orElseThrow(()-> new IllegalArgumentException("해당 게시물은 존재하지 않습니다"));
		 Page<Comment> comments=commentRepository.findByPost_Id(postId,pageable);
		 log.info("게시물 ID{}에 대한 댓글 조회 완료{}개 ",postId, comments.getTotalElements());
		 
		return comments;
		
	}
	
}
