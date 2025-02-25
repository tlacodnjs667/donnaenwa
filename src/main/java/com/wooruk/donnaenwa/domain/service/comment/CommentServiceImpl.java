package com.wooruk.donnaenwa.domain.service.comment;

import com.wooruk.donnaenwa.domain.entity.Comment;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import com.wooruk.donnaenwa.domain.repository.comment.CommentRepository;
import com.wooruk.donnaenwa.domain.repository.post.PostRepository;
import com.wooruk.donnaenwa.dto.comment.CommentCreateRequest;
import com.wooruk.donnaenwa.dto.comment.CommentDto;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import com.wooruk.donnaenwa.dto.comment.CommentUpdateRequest;
import com.wooruk.donnaenwa.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;
  private final PostRepository postRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public Page<CommentDto> getComments(Long memberId, CommentListRequest req) {
    Page<Comment> comments = commentRepository.getComments(req);

    if (comments == null) {
      return null;
    }

    return comments.map(comment -> convertToCommentDto(memberId, comment));
  }

  @Override
  public CommentDto createComment(Long memberId, CommentCreateRequest req) {

    Member member = memberRepository.findById(memberId).orElseThrow();
    Comment.CommentBuilder commentBuilder = Comment.builder().content(req.getContent()).member(member);

    if (req.getParentId() != null) {
      Comment parent = commentRepository.findById(req.getParentId()).orElseThrow();
      commentBuilder.parent(parent);
    } else if (req.getPostId() != null) {
      Post post = postRepository.findById(req.getPostId()).orElseThrow();
      commentBuilder.post(post);
    }

    Comment commentToSave = commentBuilder.build();
    commentRepository.save(commentToSave);

    return convertToCommentDto(member.getId(), commentToSave);
  }

  @Override
  public CommentDto updateComment(Long memberId, Long commentId, CommentUpdateRequest req) {
    Comment comment = commentRepository.findById(commentId).orElseThrow();

    if (!comment.getMember().getId().equals(memberId)) {
      throw new AccessDeniedException("NOT_OWNER");
    }

    comment.updateContent(req.getContent());

    commentRepository.save(comment);
    return convertToCommentDto(memberId, comment);
  }

  @Override
  public void deleteComment(Long memberId, Long commentId) {
    Comment comment = commentRepository.findById(commentId).orElseThrow();

    if (comment.getMember().getId().equals(memberId)) {
      throw new AccessDeniedException("NOT_OWNER");
    }

    commentRepository.delete(comment);
  }

  public CommentDto convertToCommentDto(Long memberId, Comment comment) {
    boolean isOwner = comment.getMember().getId().equals(memberId);

    return CommentDto.builder().id(comment.getId()).content(comment.getContent())
        .memberId(comment.getMember().getId()).membername(comment.getMember().getMembername())
        .createdAt(comment.getCreatedAt()).likes(comment.getLikes()).isEditable(isOwner)
        .isDeletable(isOwner).build();
  }
}
