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
  public PostListResponse getPostsListByCondition(PostListRequest postListRequest) {
    Page<Post> posts = postRepository.getPostsListByPagenation(postListRequest);

    Page<PostResponseListDto> postList = posts.map(this::convertToListDto);
    List<Category> categories = categoryRepository.findAll();

    return PostListResponse.builder().posts(postList).categories(categories).build();
  }


  @Override
  public PostResponseDto createPost(PostCreateRequest req) throws AccessDeniedException {

    Long memberId = jwtTokenProvider.getCurrentUserPk();
    Member member = memberRepository.findById(memberId).orElseThrow();
    Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow();


    Post post = Post.builder().member(member).category(category).title(req.getTitle())
        .content(req.getContent()).build();

    boolean isOwnerOfContent = post.getMember().getId().equals(memberId);
    postRepository.save(post);

    return this.convertToResponseDto(post, isOwnerOfContent);
  }

  @Override
  public PostResponseDto getPost(Long postId) {
    Long memberId = jwtTokenProvider.getCurrentUserPk();

    Post post = postRepository.findById(postId).orElseThrow();

    boolean isOwnerOfContent =
        memberId != null ? post.getMember().getId().equals(memberId) : Boolean.FALSE;

    return this.convertToResponseDto(post, isOwnerOfContent);
  }

  @Override
  public PostResponseDto updatePost(Long postId, PostPatchRequest req)
      throws AccessDeniedException {
    Long memberId = jwtTokenProvider.getCurrentUserPk();
    Post postToUpdate = postRepository.findById(postId).orElseThrow();

    boolean isOwnerOfContent = postToUpdate.getMember().getId().equals(memberId);

    if (!isOwnerOfContent) {
      throw new AccessDeniedException("NOT_OWNER");
    }

    postToUpdate.updateTitle(req.getTitle());
    postToUpdate.updateContent(req.getContent());

    postRepository.save(postToUpdate);

    return this.convertToResponseDto(postToUpdate, isOwnerOfContent);
  }

  @Override
  public void deletePost(Long postId) throws AccessDeniedException {
    Long memberId = jwtTokenProvider.getCurrentUserPk();
    Post post = postRepository.findById(postId).orElseThrow();

    if (!post.getMember().getId().equals(memberId)) {
      throw new AccessDeniedException("NOT_OWNER");
    }

    postRepository.delete(post);
  }

  private PostResponseDto convertToResponseDto(Post post, boolean isOwner) {
    return PostResponseDto.builder().id(post.getId()).membername(post.getMember().getMembername())
        .category(post.getCategory()).title(post.getTitle()).content(post.getContent())
        .createdAt(post.getCreatedAt()).updatedAt(post.getUpdatedAt()).isEditable(isOwner)
        .isDeletable(isOwner).build();
  }

  private PostResponseListDto convertToListDto(Post post) {

    Long memberId = jwtTokenProvider.getCurrentUserPk();
    Boolean isOwnerOfContent =
        memberId != null ? post.getMember().getId().equals(memberId) : Boolean.FALSE;

    return PostResponseListDto.builder().id(post.getId())
        .membername(post.getMember().getMembername())
        .categoryName(post.getCategory().getCategoryName()).title(post.getTitle())
        .createdAt(post.getCreatedAt()).updatedAt(post.getUpdatedAt()).isEditable(isOwnerOfContent)
        .isDeletable(isOwnerOfContent).build();

  }
}
