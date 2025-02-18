package com.wooruk.donnaenwa.security;

import com.wooruk.donnaenwa.domain.entity.Member;
import com.wooruk.donnaenwa.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonnaenwaDetailsService  implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("username  = {}", username);

    Member member = memberRepository.findByMembername(username)
        .orElseThrow(() -> new UsernameNotFoundException("UNREGISTERED_USER : " + username));
    log.debug("member  = {}", member);
    return new DonnaenwaUserDetails(member);
  }
}
