package com.wooruk.donnaenwa.controller;

import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.dto.JoinRequest;
import com.wooruk.donnaenwa.domain.service.MemberService;
import com.wooruk.donnaenwa.dto.LoginRequestDto;
import com.wooruk.donnaenwa.dto.LoginResponse;
import com.wooruk.donnaenwa.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;
  private final JwtTokenProvider jwtTokenProvider;

  @GetMapping("/hello")
  public String getHello () {
    return  "helloToMyGirl";
  }

  @PostMapping("/join")
  public ResponseEntity<String> join (@Valid @RequestBody JoinRequest joinRequest) {
    log.debug ( "LoginRequestDto = {}", joinRequest);

    JoinRequestStatus message = memberService.join(joinRequest);

    if (message.equals(JoinRequestStatus.USER_CREATED)) {
      return ResponseEntity.ok(message.name());
    }

    return ResponseEntity.badRequest().body(message.name());
  }

  @PostMapping ("/login")
  public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequestDto loginRequest) {
    Member member = memberService.login(loginRequest);

    String token = jwtTokenProvider.generateToken (member.getMembername(), member.getRoles());
    LoginResponse loginResponse = new LoginResponse(member.getMembername(), token);
    return ResponseEntity.ok(loginResponse);
  }
}
