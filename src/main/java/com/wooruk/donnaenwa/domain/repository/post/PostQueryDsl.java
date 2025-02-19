package com.wooruk.donnaenwa.domain.repository.post;

import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.dto.post.PostListRequest;
import org.springframework.data.domain.Page;

public interface PostQueryDsl {
  Page<Post> getPostsListByPagenation (PostListRequest postListRequest);
}

