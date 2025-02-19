package com.wooruk.donnaenwa.dto.post;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostPatchRequest {
  @NotBlank(message = "TITLE_REQUIRED")
  private String title;

  @NotBlank(message = "CONTENT_REQUIRED")
  private String content;
}
