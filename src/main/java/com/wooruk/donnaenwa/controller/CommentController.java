package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.domain.service.comment.CommentService;
import com.wooruk.donnaenwa.dto.comment.CommentCreateRequest;
import com.wooruk.donnaenwa.dto.comment.CommentDto;
import com.wooruk.donnaenwa.dto.comment.CommentListRequest;
import com.wooruk.donnaenwa.dto.comment.CommentUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<Page<CommentDto>> getComments (CommentListRequest req) {
    Page<CommentDto> commentPage = commentService.getComments(req);
    return ResponseEntity.ok(commentPage);
  }

  @PostMapping
  public ResponseEntity<CommentDto> createComment (CommentCreateRequest req) {

    CommentDto comment = commentService.createComment(req);

    URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("{id}")
        .buildAndExpand(comment.getId())
        .toUri();

    return ResponseEntity.created(uri).body(comment);
  }

  @PatchMapping
  public ResponseEntity<CommentDto> updateComment (CommentUpdateRequest req) {
    CommentDto commentUpdated = commentService.updateComment(req);
    return ResponseEntity.ok(commentUpdated);
  }
}
