package com.wooruk.donnaenwa.domain.service.post;

import com.wooruk.donnaenwa.dto.post.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public interface PostService {
  PostListResponse getPostsListByCondition(PostListRequest req);

  PostResponseDto createPost(PostCreateRequest req) throws AccessDeniedException;

  PostResponseDto getPost(Long postId);

  PostResponseDto updatePost(Long postId, PostPatchRequest req) throws AccessDeniedException;

  void deletePost(Long postId) throws AccessDeniedException;
}
