package com.wooruk.donnaenwa.dto.post;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class PostResponseListDto {
  private Long id;
  private String membername;
  private String category;
  private String title;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Boolean isEditable;
  private Boolean isDeletable;
}
