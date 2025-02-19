package com.wooruk.donnaenwa.domain.repository.post;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.domain.entity.QPost;
import com.wooruk.donnaenwa.dto.post.PostListRequest;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Objects;

public class PostQueryDslImpl extends QuerydslRepositorySupport implements PostQueryDsl {
  public PostQueryDslImpl () {
    super(Post.class);
  }

  private final QPost post = QPost.post;

  @Override
  public Page<Post> getPostsListByPagenation(PostListRequest postListRequest) {
    JPQLQuery<Post> query = from(post);
    BooleanBuilder bool = new BooleanBuilder();

//    PostListRequest. search Keyword
    bool.and(post.title.containsIgnoreCase(postListRequest.getKeyword()));
    bool.or(post.content.containsIgnoreCase(postListRequest.getKeyword()));

    query.where(bool);

    Pageable pageable = PageRequest.of(postListRequest.getPage(), postListRequest.getCount(), Sort.Direction.ASC, "id");

    Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query);
    List<Post> posts = query.fetch();
    long count = query.fetchCount();

    return new PageImpl<>(posts, pageable, count);
  }

}
