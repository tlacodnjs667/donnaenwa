package com.wooruk.donnaenwa.domain.service.comment;

import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import com.wooruk.donnaenwa.domain.repository.comment.CommentRepository;
import com.wooruk.donnaenwa.domain.repository.post.PostRepository;
import com.wooruk.donnaenwa.dto.comment.CommentCreateResponse;
import com.wooruk.donnaenwa.dto.comment.CommentDto;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import com.wooruk.donnaenwa.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{
  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;
  private final PostRepository postRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public Page<CommentDto> getComments (CommentListRequest req) {
    Page<Comment> comments = commentRepository.getComments(req);

    if (comments == null) {
      return null;
    }

    return comments.map(this::convertToCommentDto);
  }

  @Override
  public CommentDto createComment(CommentCreateResponse req) {
    Member member = memberRepository.findById(jwtTokenProvider.getCurrentUserPk()).orElseThrow();

    Comment.CommentBuilder commentBuilder = Comment.builder()
        .content(req.getContent())
        .member(member);

    if (req.getParentId() != null) {
      Comment parent = commentRepository.findById(req.getParentId()).orElseThrow();
      commentBuilder.parent(parent);
    } else if (req.getPostId() != null) {
      Post post = postRepository.findById(req.getPostId()).orElseThrow();
      commentBuilder.post(post);
    }

    Comment commentToSave = commentBuilder.build();
    commentRepository.save(commentToSave);

    return convertToCommentDto(commentToSave);
  }

  public CommentDto convertToCommentDto (Comment comment) {
    return CommentDto.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .memberId(comment.getMember().getId())
        .membername(comment.getMember().getMembername())
        .createdAt(comment.getCreatedAt())
        .likes(comment.getLikes())
        .build();
  }
}
