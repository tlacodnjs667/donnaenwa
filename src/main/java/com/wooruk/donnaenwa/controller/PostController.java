package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.domain.service.post.PostService;
import com.wooruk.donnaenwa.dto.post.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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
  public ResponseEntity<PostListResponse> getPosts (@ModelAttribute PostListRequest request) {
    PostListResponse res = postService.getPostsListByCondition(request);
    return ResponseEntity.ok(res);
  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PostResponseDto> post (@Valid @RequestBody PostCreateRequest postCreateRequest)
      throws AccessDeniedException {
    PostResponseDto createdPost = postService.createPost(postCreateRequest);

    if (createdPost == null) {
      return ResponseEntity.badRequest().build();
    }

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdPost.getId())
        .toUri();

    return ResponseEntity.created(location).body(createdPost);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDto> getPost (@PathVariable Long postId) {
    PostResponseDto res = postService.getPost(postId);
    return ResponseEntity.ok(res);
  }

  @PutMapping("/{postId}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PostResponseDto> putPost (@PathVariable Long postId, @Valid @RequestBody PostPatchRequest postPatchRequest) {
    PostResponseDto postUpdated = postService.updatePost(postId, postPatchRequest);
    return ResponseEntity.ok(postUpdated);
  }
}
