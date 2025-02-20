package com.wooruk.donnaenwa.domain.service.post;

import com.wooruk.donnaenwa.domain.entity.Category;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.entity.Post;
import com.wooruk.donnaenwa.domain.repository.CategoryRepository;
import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import com.wooruk.donnaenwa.domain.repository.post.PostRepository;
import com.wooruk.donnaenwa.dto.post.*;
import com.wooruk.donnaenwa.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  private final CategoryRepository categoryRepository;
  private final MemberRepository memberRepository;

  private final JwtTokenProvider jwtTokenProvider;


  @Override
  public PostListResponse getPostsListByCondition(Long memberId, PostListRequest postListRequest) {
    Page<Post> posts = postRepository.getPostsListByPagenation(postListRequest);

    Page<PostResponseListDto> postList = posts.map(post -> convertToListDto(memberId, post));
    List<Category> categories = categoryRepository.findAll();

    return PostListResponse.builder().posts(postList).categories(categories).build();
  }


  @Override
  public PostResponseDto createPost(Long memberId, PostCreateRequest req)
      throws AccessDeniedException {

    Member member = memberRepository.findById(memberId).orElseThrow();
    Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow();


    Post post = Post.builder().member(member).category(category).title(req.getTitle())
        .content(req.getContent()).build();

    boolean isOwnerOfContent = post.getMember().getId().equals(memberId);
    postRepository.save(post);

    return this.convertToResponseDto(memberId, post);
  }

  @Override
  public PostResponseDto getPost(Long memberId, Long postId) {
    Post post = postRepository.findById(postId).orElseThrow();

    boolean isOwnerOfContent =
        memberId != null ? post.getMember().getId().equals(memberId) : Boolean.FALSE;

    return this.convertToResponseDto(memberId, post);
  }

  @Override
  public PostResponseDto updatePost(Long memberId, Long postId, PostPatchRequest req)
      throws AccessDeniedException {
    Post postToUpdate = postRepository.findById(postId).orElseThrow();

    boolean isOwnerOfContent = postToUpdate.getMember().getId().equals(memberId);

    if (!isOwnerOfContent) {
      throw new AccessDeniedException("NOT_OWNER");
    }

    postToUpdate.updateTitle(req.getTitle());
    postToUpdate.updateContent(req.getContent());

    postRepository.save(postToUpdate);

    return this.convertToResponseDto(memberId, postToUpdate);
  }

  @Override
  public void deletePost(Long memberId, Long postId) throws AccessDeniedException {
    Post post = postRepository.findById(postId).orElseThrow();

    if (!post.getMember().getId().equals(memberId)) {
      throw new AccessDeniedException("NOT_OWNER");
    }

    postRepository.delete(post);
  }

  private PostResponseDto convertToResponseDto(Long memberId, Post post) {
    boolean isOwnerOfContent = post.getMember().getId().equals(memberId);

    return PostResponseDto.builder().id(post.getId()).membername(post.getMember().getMembername())
        .category(post.getCategory()).title(post.getTitle()).content(post.getContent())
        .createdAt(post.getCreatedAt()).updatedAt(post.getUpdatedAt()).isEditable(isOwnerOfContent)
        .isDeletable(isOwnerOfContent).build();
  }

  private PostResponseListDto convertToListDto(Long memberId, Post post) {
    boolean isOwnerOfContent = post.getMember().getId().equals(memberId);

    return PostResponseListDto.builder().id(post.getId())
        .membername(post.getMember().getMembername())
        .categoryName(post.getCategory().getCategoryName()).title(post.getTitle())
        .createdAt(post.getCreatedAt()).updatedAt(post.getUpdatedAt()).isEditable(isOwnerOfContent)
        .isDeletable(isOwnerOfContent).build();

  }
}
