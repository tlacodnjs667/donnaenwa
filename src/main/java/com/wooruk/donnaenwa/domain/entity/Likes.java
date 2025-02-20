package com.wooruk.donnaenwa.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
public class Likes {
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
