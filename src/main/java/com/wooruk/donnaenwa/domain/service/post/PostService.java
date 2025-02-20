package com.wooruk.donnaenwa.domain.service.post;

import com.wooruk.donnaenwa.dto.post.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


@Service
public interface PostService {
  PostListResponse getPostsListByCondition(Long memberId, PostListRequest req);

  PostResponseDto createPost(Long memberId, PostCreateRequest req) throws AccessDeniedException;

  PostResponseDto getPost(Long memberId, Long postId);

  PostResponseDto updatePost(Long memberId, Long postId, PostPatchRequest req) throws AccessDeniedException;

  void deletePost(Long memberId, Long postId) throws AccessDeniedException;
}
