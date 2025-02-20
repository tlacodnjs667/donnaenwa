package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.domain.service.post.PostService;
import com.wooruk.donnaenwa.dto.post.*;
import com.wooruk.donnaenwa.security.DonnaenwaUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
  private final PostService postService;

  @GetMapping
  public ResponseEntity<PostListResponse> getPosts(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails,
      @ModelAttribute PostListRequest request) {
    Long memberId = userDetails.getId();
    PostListResponse res = postService.getPostsListByCondition(memberId, request);
    return ResponseEntity.ok(res);
  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PostResponseDto> post(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails,
      @Valid @RequestBody PostCreateRequest postCreateRequest) throws AccessDeniedException {

    Long memberId = userDetails.getId();
    PostResponseDto createdPost = postService.createPost(memberId, postCreateRequest);

    if (createdPost == null) {
      return ResponseEntity.badRequest().build();
    }

    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdPost.getId()).toUri();

    return ResponseEntity.created(location).body(createdPost);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDto> getPost(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails, @PathVariable Long postId) {
    Long memberId = userDetails.getId();
    PostResponseDto res = postService.getPost(memberId, postId);
    return ResponseEntity.ok(res);
  }

  @PutMapping("/{postId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PostResponseDto> putPost(
      @AuthenticationPrincipal DonnaenwaUserDetails userDetails, @PathVariable Long postId,
      @Valid @RequestBody PostPatchRequest postPatchRequest) {
    Long memberId = userDetails.getId();
    PostResponseDto postUpdated = postService.updatePost(memberId, postId, postPatchRequest);
    return ResponseEntity.ok(postUpdated);
  }

  @DeleteMapping("/{postId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Void> deletePost(@AuthenticationPrincipal DonnaenwaUserDetails userDetails,
      @PathVariable Long postId) {
    Long memberId = userDetails.getId();
    postService.deletePost(memberId, postId);
    return ResponseEntity.noContent().build();
  }
}
