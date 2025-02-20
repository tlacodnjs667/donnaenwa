package com.wooruk.donnaenwa.dto.comment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = CommentUpdateRequest.CommentUpdateRequestBuilder.class)
public class CommentUpdateRequest {
  private Long commentId;
  private String content;
}
