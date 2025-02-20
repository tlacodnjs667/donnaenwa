package com.wooruk.donnaenwa.domain.repository.comment;

import com.wooruk.donnaenwa.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryDsl {
}
