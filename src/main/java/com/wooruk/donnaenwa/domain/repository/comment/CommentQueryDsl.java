package com.wooruk.donnaenwa.domain.repository.comment;

import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import org.springframework.data.domain.Page;

public interface CommentQueryDsl {
  Page<Comment> getComments (CommentListRequest req);
}
