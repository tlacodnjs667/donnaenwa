package com.wooruk.donnaenwa.domain.service.like;

import com.wooruk.donnaenwa.dto.like.LikePostRequest;

public interface LikeService {
  String postLike (Long memberId, LikePostRequest request);
}
