package com.wooruk.donnaenwa.domain.repository;

import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.domain.entity.Likes;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
  Optional<Likes> findByMemberAndComment (Member member, Comment comment);
  Optional<Likes> findByMemberAndPost (Member member, Post post);
}
