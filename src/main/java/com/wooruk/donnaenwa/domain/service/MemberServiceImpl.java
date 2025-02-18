package com.wooruk.donnaenwa.domain.service;

import com.wooruk.donnaenwa.controller.JoinRequestStatus;
import com.wooruk.donnaenwa.dto.member.JoinRequest;
import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.dto.member.LoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;


  @Override
  public JoinRequestStatus join(JoinRequest joinRequest) {

    boolean existUserName = memberRepository.existsByMembername(joinRequest.getName());
    log.debug("exist = {}", existUserName);
    if (existUserName) {
      return JoinRequestStatus.USERNAME_EXISTED;
    }

    boolean existEmail = memberRepository.existsByMembername(joinRequest.getEmail());
    log.debug("exist = {}", existEmail);
    if (existEmail) {
      return JoinRequestStatus.EMAIL_EXISTED;
    }


    Member member = Member.builder().membername(joinRequest.getName())
        .password(passwordEncoder.encode(joinRequest.getPassword()))
        .phoneNumber(joinRequest.getPhoneNumber()).email(joinRequest.getEmail()).build();

    log.debug("member = {}", member);

    memberRepository.save(member);
    return JoinRequestStatus.USER_CREATED;
  }

  @Override
  public Member login(LoginRequestDto loginRequest) {
    Member member = memberRepository.findByMembername(loginRequest.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("USERNAME_NOT_FOUND"));

    if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
      throw new BadCredentialsException("NOT_MATCH_PASSWORD");
    }

    return member;
  }

}
