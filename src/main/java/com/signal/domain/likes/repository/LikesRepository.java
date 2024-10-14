package com.signal.domain.likes.repository;

import com.signal.domain.likes.model.Likes;
import com.signal.global.exception.errorCode.ErrorCode;
import com.signal.global.exception.handler.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("SELECT l FROM Likes l WHERE l.post.id = :postId AND l.user.id = :userId")
    Likes findLikesByPostIdAndUserId(Long postId, Long userId);

    default boolean existsLikesById(Long postId, Long userId) {
        return findLikesByPostIdAndUserId(postId, userId) != null;
    }

    default Likes findLikesById(Long likesId) {
        return findById(likesId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.NOT_FOUND));
    }
}
