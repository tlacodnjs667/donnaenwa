package com.wooruk.donnaenwa.dto.comment;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = CommentCreateRequest.CommentCreateRequestBuilder.class)
public class CommentCreateRequest {
  private String content;
  private Long postId;
  private Long parentId;
}
// parentId와 postId 값이 동시에 들어올 경우, 대댓글로 간주
