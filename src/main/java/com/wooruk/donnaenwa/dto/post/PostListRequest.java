package com.wooruk.donnaenwa.dto.post;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonDeserialize(builder = PostListRequest.PostListRequestBuilder.class)
public class PostListRequest {
  @Builder.Default
  private Long categoryId=null;

  @Builder.Default
  private Integer count = 15;

  @Builder.Default
  private String search_keyword = "";

  @Builder.Default
  private Integer curPage = 0;

  @Builder.Default
  private String order = "createdTimeASC";
}
