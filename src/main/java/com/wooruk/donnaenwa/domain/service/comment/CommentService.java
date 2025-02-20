package com.wooruk.donnaenwa.domain.service.comment;

import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.dto.comment.CommentCreateResponse;
import com.wooruk.donnaenwa.dto.comment.CommentDto;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import org.springframework.data.domain.Page;

public interface CommentService {
  Page<CommentDto> getComments (CommentListRequest req);


  CommentDto createComment (CommentCreateResponse req);
}
