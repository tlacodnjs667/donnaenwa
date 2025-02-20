package com.wooruk.donnaenwa.domain.repository.comment;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.domain.entity.QComment;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class CommentQueryDslImpl  extends QuerydslRepositorySupport implements CommentQueryDsl {
  public CommentQueryDslImpl() {
    super(Comment.class);
  }

  private final QComment comment = QComment.comment;

  public Page<Comment> getComments (CommentListRequest req) {
    JPQLQuery<Comment> query = from(comment);

    BooleanBuilder bool = new BooleanBuilder();

    if (req.getPostId() != null) {
      bool.and(comment.post.id.eq(req.getPostId()));
    } else if (req.getCommentId() != null) {
      bool.and(comment.parent.id.eq(req.getCommentId()));
    } else {
      return null;
    }

    query.where(bool);
    long count = query.fetchCount();

    Pageable pageable = PageRequest.of(req.getPage(), req.getCount(), Sort.Direction.DESC, "id");

    Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);

    List<Comment> comments = query.fetch();

    return new PageImpl<>(comments, pageable, count);
  }
}
