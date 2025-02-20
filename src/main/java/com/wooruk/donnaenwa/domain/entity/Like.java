package com.wooruk.donnaenwa.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity(name = "likes")
public class Like {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn
  private Comment comment;
}
