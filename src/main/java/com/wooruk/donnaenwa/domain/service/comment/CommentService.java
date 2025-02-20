package com.wooruk.donnaenwa.domain.service.comment;

import com.wooruk.donnaenwa.dto.comment.CommentCreateRequest;
import com.wooruk.donnaenwa.dto.comment.CommentDto;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import com.wooruk.donnaenwa.dto.comment.CommentUpdateRequest;
import org.springframework.data.domain.Page;

public interface CommentService {
  Page<CommentDto> getComments (Long memberId, CommentListRequest req);
  CommentDto createComment (Long memberId, CommentCreateRequest req);
  CommentDto updateComment (Long memberId, Long commentId, CommentUpdateRequest req);
  void deleteComment (Long memberId, Long commentId);

}
