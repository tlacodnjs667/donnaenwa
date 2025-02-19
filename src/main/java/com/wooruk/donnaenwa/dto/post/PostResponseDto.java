package com.wooruk.donnaenwa.dto.post;

import com.wooruk.donnaenwa.domain.entity.Category;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class PostResponseDto {
  private Long id;
  private String membername;
  private Category category;
  private String title;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean isEditable;
  private Boolean isDeletable;
}
