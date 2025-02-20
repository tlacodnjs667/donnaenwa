package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.domain.service.like.LikeService;
import com.wooruk.donnaenwa.dto.like.LikePostRequest;
import com.wooruk.donnaenwa.security.DonnaenwaUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

  private final LikeService likeService;

  @GetMapping
  public ResponseEntity<String> like (
      @AuthenticationPrincipal DonnaenwaUserDetails donnaenwaUserDetails,
      LikePostRequest req
  ) {
    String message = likeService.postLike(donnaenwaUserDetails.getId(), req);

    if (message.equals("LIKE_DELETED")) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(message);
  }

}
