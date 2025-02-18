package com.wooruk.donnaenwa.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class Member implements Persistable<Long> {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String membername;

  @Column(unique = true, nullable = false)
  private String phoneNumber;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name="user_roles",
      joinColumns = @JoinColumn(name = "user_index")
  )
  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Set<Role> roles = new HashSet<>();

  @PrePersist
  public void setDefaultRole () {
    if (roles == null) {
      roles = new HashSet<>();
    }
    roles.add(Role.USER);
  }

  @Override
  public boolean isNew() {
    return this.id == null;
  }
}
