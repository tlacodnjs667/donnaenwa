package com.wooruk.donnaenwa.dto.comment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@ToString
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = CommentListRequest.CommentListRequestBuilder.class)
public class CommentListRequest {
  private Long postId = null;
  private Long commentId = null;
  @Builder.Default
  private Integer count = 5;
  @Builder.Default
  private Integer page = 0;
  @Builder.Default
  private String order = "createdTimeDESC";
}
