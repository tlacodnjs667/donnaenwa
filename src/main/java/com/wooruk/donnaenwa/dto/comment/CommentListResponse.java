package com.wooruk.donnaenwa.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponse {
  private List<CommentDto> comments;
  private Integer totalPage;
  private Integer curPage;
 e
}
