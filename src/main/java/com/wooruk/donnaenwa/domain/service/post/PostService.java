package com.wooruk.donnaenwa.domain.service.post;

import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.dto.post.PostCreateRequest;
import com.wooruk.donnaenwa.dto.post.PostListRequest;
import com.wooruk.donnaenwa.dto.post.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
  PostListResponse getPostsListByCondition (PostListRequest postListRequest);
  Post createPost (PostCreateRequest req);
}
// Page<Restaurant> restaurants
