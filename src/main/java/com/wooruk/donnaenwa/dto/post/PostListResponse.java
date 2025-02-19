package com.wooruk.donnaenwa.dto.post;

import com.wooruk.donnaenwa.domain.entity.Category;
import com.wooruk.donnaenwa.domain.entity.Post;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PostListResponse {
  private Page<PostResponseListDto> posts;
  private List<Category> categories;
}
