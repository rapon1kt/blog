package com.raponi.blog.domain.repository;

import java.util.Optional;

import com.raponi.blog.domain.model.Likable;
import com.raponi.blog.domain.model.LikeTargetType;

public interface LikableRepository {

  Optional<Likable> findById(String id, LikeTargetType type);

  void save(Likable likable);

}
