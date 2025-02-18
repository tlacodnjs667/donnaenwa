package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.domain.service.post.PostService;
import com.wooruk.donnaenwa.dto.post.PostCreateRequest;
import com.wooruk.donnaenwa.dto.post.PostListRequest;
import com.wooruk.donnaenwa.dto.post.PostListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
  private final PostService postService;

  @GetMapping
  public ResponseEntity<PostListResponse> getPosts (PostListRequest request) {
    PostListResponse res = postService.getPostsListByCondition(request);
    return ResponseEntity.ok(res);
  }

  public ResponseEntity<Post> post (PostCreateRequest postCreateRequest) {
    Post createdPost = postService.createPost(postCreateRequest);

    if (createdPost == null) {
      return ResponseEntity.badRequest().build();
    }

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdPost.getId())
        .toUri();

    return ResponseEntity.created(location).body(createdPost);
  }
}
