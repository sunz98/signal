package com.signal.domain.post.repository;

import com.signal.domain.post.model.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
   List<Post> findByUser_Id(Long id);
   List<Post> findByCategory(String username);
   List<Post> findByTitle(String title);
   



}
