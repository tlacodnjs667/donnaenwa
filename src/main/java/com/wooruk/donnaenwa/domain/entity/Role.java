package com.wooruk.donnaenwa.domain.entity;

import lombok.Getter;

@Getter
public enum Role {
  USER("ROLE_USER"), GUEST("ROLE_GUEST");

  final String role;

  Role(String role) {
    this.role = role;
  }

  ;

}
