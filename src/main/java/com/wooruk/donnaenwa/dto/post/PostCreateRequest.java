package com.wooruk.donnaenwa.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequest {
  @NotBlank(message = "TITLE_REQUIRED")
  private String title;

  @NotBlank(message = "CONTENT_REQUIRED")
  private String content;

  @NotNull(message = "CATEGORY_ID_REQUIRED")
  private Integer categoryId;
}
