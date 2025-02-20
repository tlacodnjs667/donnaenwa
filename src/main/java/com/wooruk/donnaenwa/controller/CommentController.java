package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.DonnaenwaApplication;
import com.wooruk.donnaenwa.domain.service.comment.CommentService;
import com.wooruk.donnaenwa.dto.comment.CommentCreateRequest;
import com.wooruk.donnaenwa.dto.comment.CommentDto;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import com.wooruk.donnaenwa.dto.comment.CommentUpdateRequest;
import com.wooruk.donnaenwa.security.DonnaenwaUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
  private final CommentService commentService;

  @GetMapping
  public ResponseEntity<Page<CommentDto>> getComments(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails, CommentListRequest req) {
    Long memberId = userDetails != null ? userDetails.getId() : null;
    Page<CommentDto> commentPage = commentService.getComments(memberId, req);
    return ResponseEntity.ok(commentPage);
  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CommentDto> createComment(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails, CommentCreateRequest req) {
    Long memberId = userDetails.getId();
    CommentDto comment = commentService.createComment(memberId, req);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}")
        .buildAndExpand(comment.getId()).toUri();

    return ResponseEntity.created(uri).body(comment);
  }

  @PatchMapping("/{commentId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<CommentDto> updateComment(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails, @PathVariable Long commentId,
      CommentUpdateRequest req) {
    Long memberId = userDetails.getId();
    CommentDto commentUpdated = commentService.updateComment(memberId, commentId, req);
    return ResponseEntity.ok(commentUpdated);
  }

  @DeleteMapping("/{commentId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> deleteComment(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails, @PathVariable Long commentId) {
    Long memberId = userDetails.getId();
    commentService.deleteComment(memberId, commentId);
    return ResponseEntity.noContent().build();
  }
}
