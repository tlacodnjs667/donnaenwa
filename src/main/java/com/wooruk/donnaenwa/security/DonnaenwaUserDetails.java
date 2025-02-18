package com.wooruk.donnaenwa.security;

import com.wooruk.donnaenwa.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DonnaenwaUserDetails implements UserDetails {

  @Getter
  private Long id;
  private String username;
  private String phoneNumber;
  private String email;
  private final String password;
  private final Set<GrantedAuthority> authorities;

  public DonnaenwaUserDetails (Member member) {
     this.id = member.getId();
     this.username = member.getMembername();
     this.phoneNumber = member.getPhoneNumber();
     this.password = member.getPassword();
     this.email = member.getEmail();
     this.authorities = member.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
         .collect(Collectors.toSet());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
