package com.wooruk.donnaenwa.domain.service.like;

import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.domain.entity.Likes;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.domain.repository.LikeRepository;
import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import com.wooruk.donnaenwa.domain.repository.comment.CommentRepository;
import com.wooruk.donnaenwa.domain.repository.post.PostRepository;
import com.wooruk.donnaenwa.dto.like.LikePostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

  private final LikeRepository likeRepository;
  private final MemberRepository memberRepository;
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  @Override
  public String postLike(Long memberId, LikePostRequest request) {

    Member member = memberRepository.findById(memberId).orElseThrow();
    Likes likes = null;

    if (request.getCommentId() != null) {
      Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow();
      Optional<Likes> like = likeRepository.findByMemberAndComment(member, comment);

      if (like.isPresent()) {
        likeRepository.delete(like.get());
        return "LIKE_DELETED";
      }

      likes = Likes.builder().member(member).comment(comment).build();

    } else if (request.getPostId() != null) {
      Post post = postRepository.findById(request.getPostId()).orElseThrow();
      Optional<Likes> like = likeRepository.findByMemberAndPost(member, post);

      if (like.isPresent()) {
        likeRepository.delete(like.get());
        return "LIKE_DELETED";
      }

      likes = Likes.builder().member(member).post(post).build();
    } else {
      throw new NoSuchElementException("NO_FULFILL_REQUIRED_VALUES");
    }

    likeRepository.save(likes);
    return "LIKE_SAVED";
  }
}
