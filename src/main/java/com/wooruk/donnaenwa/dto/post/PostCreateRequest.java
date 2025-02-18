package com.wooruk.donnaenwa.dto.post;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequest {
  private String title;
  private String content;
  private Integer categoryId;
}
