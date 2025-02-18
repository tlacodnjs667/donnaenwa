package com.wooruk.donnaenwa.domain.service.post;

import com.wooruk.donnaenwa.domain.entity.Category;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.domain.repository.CategoryRepository;
import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import com.wooruk.donnaenwa.domain.repository.post.PostRepository;
import com.wooruk.donnaenwa.dto.post.PostListRequest;
import com.wooruk.donnaenwa.dto.post.PostListResponse;
import com.wooruk.donnaenwa.dto.post.PostResponseListDto;
import com.wooruk.donnaenwa.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService{

  private final PostRepository postRepository;

  private final CategoryRepository categoryRepository;
  private final MemberRepository memberRepository;

  private final JwtTokenProvider jwtTokenProvider;


  @Override
  public PostListResponse getPostsListByCondition(PostListRequest postListRequest) {
    Page<Post> posts = postRepository.getPostsListByPagenation(postListRequest);

    Page<PostResponseListDto> postList = posts.map(this::convertToListDto);
    List<Category> categories = categoryRepository.findAll();

    return PostListResponse.builder()
        .posts(postList)
        .categories(categories)
        .build();
  }

  private PostResponseListDto convertToListDto (Post post) {

    Long memberId = jwtTokenProvider.getCurrentUserPk();
    Boolean isOwnerOfContent = memberId != null ? post.getMember().getId().equals(memberId) : Boolean.FALSE;

    return PostResponseListDto.builder()
        .id(post.getId())
        .membername(post.getMember().getMembername())
        .category(post.getCategory().getCategoryName())
        .title(post.getTitle())
        .createdAt(post.getCreatedAt())
        .updatedAt(post.getUpdatedAt())
        .isEditable(isOwnerOfContent)
        .isDeletable(isOwnerOfContent)
        .build();

  }

  @Override
  public Post createPost() {
    Long memberId = jwtTokenProvider.getCurrentUserPk();

    if (memberId == null) {
      return null;
    }

    Member member = memberRepository.findById(memberId).orElseThrow();
    Category category = categoryRepository.findById(1).orElseThrow();

    Post post = Post.builder()
        .member(member)
        .category(category)
        .title("")
        .content("")
        .build();

    postRepository.save(post);
    return post;
  }
}
