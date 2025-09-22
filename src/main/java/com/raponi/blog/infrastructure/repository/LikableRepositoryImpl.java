package com.raponi.blog.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.raponi.blog.domain.model.Comment;
import com.raponi.blog.domain.model.Likable;
import com.raponi.blog.domain.model.LikeTargetType;
import com.raponi.blog.domain.model.Post;
import com.raponi.blog.domain.repository.CommentRepository;
import com.raponi.blog.domain.repository.LikableRepository;
import com.raponi.blog.domain.repository.PostRepository;

@Repository
public class LikableRepositoryImpl implements LikableRepository {
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;

  public LikableRepositoryImpl(PostRepository postRepository,
      CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
  }

  @Override
  public Optional<Likable> findById(String id, LikeTargetType type) {
    return switch (type) {
      case POST -> this.postRepository.findById(id).map(p -> (Likable) p);
      case COMMENT -> this.commentRepository.findById(id).map(c -> (Likable) c);
    };
  }

  @Override
  public void save(Likable likable) {
    if (likable instanceof Post post) {
      postRepository.save(post);
    } else if (likable instanceof Comment comment) {
      commentRepository.save(comment);
    }
  }
}
