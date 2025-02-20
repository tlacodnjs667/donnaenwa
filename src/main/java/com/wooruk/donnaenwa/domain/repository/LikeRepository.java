package com.wooruk.donnaenwa.domain.repository;

import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.domain.entity.Like;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
  Optional<Like> findByMemberAndComment (Member member, Comment comment);
  Optional<Like> findByMemberAndPost (Member member, Post post);
}
