package com.wooruk.donnaenwa.dto.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikePostRequest {
  private Long postId;
  private Long commentId;
}
