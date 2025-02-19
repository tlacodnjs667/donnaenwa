package com.wooruk.donnaenwa.dto.post;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonDeserialize(builder = PostPatchRequest.PostPatchRequestBuilder.class)
public class PostPatchRequest {

  @Setter
  @Builder.Default
  private Long postId = null;

  @Builder.Default
  private String title="";

  @Builder.Default
  private String content="";

}
