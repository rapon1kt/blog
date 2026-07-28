package com.raponi.blog.application.usecase.comment;

import com.raponi.blog.domain.model.Comment;
import java.util.List;

public interface FindAllCommentAnswersUseCase {
  List<Comment> handle(String commentId);
}
