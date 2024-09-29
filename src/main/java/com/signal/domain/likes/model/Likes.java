package com.signal.domain.likes.model;

import com.signal.domain.article.model.Article;
import com.signal.domain.post.model.Post;
import com.signal.global.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "likes")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Likes extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
