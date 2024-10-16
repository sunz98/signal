package com.signal.domain.likes.service;

import com.signal.domain.auth.model.User;
import com.signal.domain.auth.repository.AuthRepository;
import com.signal.domain.likes.model.Likes;
import com.signal.domain.likes.repository.LikesRepository;
import com.signal.domain.post.model.Post;
import com.signal.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final AuthRepository authRepository;
    private final PostRepository postRepository;

    public String likesPost(Long postId, Long userId) {
        User user = authRepository.findUserById(userId);
        Post post = postRepository.findPostById(postId);

        if (likesRepository.existsLikesById(postId, userId)) {
            Likes likes = likesRepository.findLikesByPostIdAndUserId(postId, userId);
            likesRepository.delete(likes);
            postRepository.decrementLikesCountById(postId);
            return "Unlike Success";
        } else {
            Likes likes = Likes.createPostLike(post, user);
            likesRepository.save(likes);
            postRepository.incrementLikesCountById(postId);
            return "Like Success";
        }
    }
}
