package com.wooruk.donnaenwa.dto.comment;

import lombok.*;

import java.time.LocalDateTime;


@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
  private Long id;
  private String content;
  private Long memberId;
  private String membername;
  private LocalDateTime createdAt;
  private Integer likes;
  private boolean isEditable;
  private boolean isDeletable;
}
