package com.wooruk.donnaenwa.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
  @JoinColumn(name = "member_id")
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
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Comment> children = new ArrayList<>(); // 자식 댓글 목록
}
