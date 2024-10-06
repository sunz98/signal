package com.signal.domain.post.repository;

import com.signal.domain.post.model.Post;
import com.signal.domain.post.model.enums.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {


   @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL AND p.category =: category ORDER BY p.viewCount DESC")
   Post findTopByOrderByViewCountDesc(Category category);

   @Query("SELECT p FROM Post p WHERE p.deletedAt IS NULL AND p.category =: category")
   Page<Post> findByCategory(Category category, Pageable pageable);

   boolean existsById(Long id);
}
