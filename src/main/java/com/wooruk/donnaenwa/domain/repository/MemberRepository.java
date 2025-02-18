package com.wooruk.donnaenwa.domain.repository;

import com.wooruk.donnaenwa.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByMembername(String memberName);
  boolean existsByMembername(String memberName);
}
