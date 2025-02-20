package com.wooruk.donnaenwa.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "member_id", nullable = false)
  @JsonIgnore
  private Member member;

  @ManyToOne
  @JoinColumn(name = "post_id")
  @JsonIgnore
  private Post post;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  @JsonIgnore
  private Comment parent;

  @CreationTimestamp
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @ColumnDefault("0")
  private Integer likes;

  @JsonIgnore
  @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Comment> children = new ArrayList<>(); // 자식 댓글 목록
}
