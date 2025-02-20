package com.wooruk.donnaenwa.domain.entity;

import jakarta.persistence.*;

@Entity
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
